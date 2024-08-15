package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ConsentRequestDto;
import com.oasis.ocrspring.dto.ConsentResponseDto;
import com.oasis.ocrspring.dto.ErrorResponseDto;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository PatientRepo;

    @Autowired
    private TeleconEntriesRepository TeleconEntriesRepo;

    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private TeleconEntriesService teleconServ;
    @Value("src/main/Storage/ConsentForms")
    private String consentFormUploadDir;

    public List<Patient> AllPatientDetails(){

        return PatientRepo.findAll();
    }
    public Patient createPatient(Patient patient){

        return PatientRepo.save(patient);
    }

    public Patient getPatientById(String id){
        return PatientRepo.findById(id).orElse(null);
    }

    public boolean isexist(String id){
        return PatientRepo.existsById(id);
    }

    public Patient sharedPatient(String id, String review_id){
        Optional<TeleconEntry> entry = TeleconEntriesRepo.findByPatientAndReviewersIn(id, review_id);
        Optional<Patient> patient = PatientRepo.findById(id);

        if (entry.isPresent() && patient.isPresent()) {
            return patient.get();
        }
        else {
            return null;
        }
    }

public Patient findOne(String patient_id, String clinician_id){
    Patient patient =  PatientRepo.findByPatientIdAndClinicianId(patient_id,new ObjectId(clinician_id)).orElse(null);
    return patient;
}
public  Patient findPatient(String id,String clinician_Id){
        ObjectId id_ = new ObjectId(id);
        ObjectId clinician_Id_ = new ObjectId(clinician_Id);
        Patient newPatient = PatientRepo.findByIdAndClinicianId(id_,clinician_Id_).orElse(null);
        return newPatient;
}
    public ResponseEntity<?> addPatient(
            String id,
            ConsentRequestDto data,
            MultipartFile files) throws IOException
    {
        List<String> uploadedURIs = new ArrayList<>();

        //permission check
//        if(!checkPermissions(req.permissions, [300])){
//            return res.status(401).json({ message: "Unauthorized access"});
//        }
        try{
            Patient patient = findOne(data.getPatient_id(),id);
            if(patient != null){
                return ResponseEntity.status(401).body("Patient ID already exists");
            }
            String fileName = StringUtils.cleanPath(files.getOriginalFilename());
            try{
                Path path = Paths.get(consentFormUploadDir + File.separator + fileName);
                if(!Files.exists(path)){
                    Files.createDirectories(path);
                }
                Files.copy(files.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                String fileDownUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/files")
                        .path(fileName)
                        .toUriString();
                uploadedURIs.add(fileDownUri);

                Patient newPatient = new Patient();
                newPatient.setPatientId(data.getPatient_id());
                newPatient.setClinicianId(new ObjectId(data.getClinician_id()));
                newPatient.setPatientName(data.getPatient_name());
                newPatient.setRiskFactors(data.getRisk_factors());
                newPatient.setHistoDiagnosis(data.getHisto_diagnosis());
                newPatient.setMedicalHistory(data.getMedical_history());
                newPatient.setFamilyHistory(data.getFamily_history());
                newPatient.setSystemicDisease(data.getSystemic_disease());
                newPatient.setContactNo(data.getContact_no());
                newPatient.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                newPatient.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

                PatientRepo.save(newPatient);
                return  ResponseEntity.status(200).body(new ConsentResponseDto(newPatient));


            }catch(MultipartException ex){
                return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                        "Server Error!",ex.toString()));

            }catch(Exception e){
                return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                        "Server Error!",e.toString()));
            }



        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                    "Server Error",e.toString()));
        }
    }



}
