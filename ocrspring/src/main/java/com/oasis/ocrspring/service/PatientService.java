package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ConsentRequestDto;
import com.oasis.ocrspring.dto.ConsentResponseDto;
import com.oasis.ocrspring.dto.ErrorResponseDto;
import com.oasis.ocrspring.dto.UpdatePatientDto;
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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private TeleconEntriesRepository teleconEntriesRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TeleconEntriesService teleconServ;
    @Value("src/main/Storage/ConsentForms")
    private String consentFormUploadDir;

    public List<Patient> allPatientDetails() {
        return patientRepo.findAll();
    }

    public Patient createPatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Patient getPatientById(String id) {
        return patientRepo.findById(id).orElse(null);
    }

    public boolean isExist(String id) {
        return patientRepo.existsById(id);
    }

    public Patient sharedPatient(String id, String reviewId) {
        Optional<TeleconEntry> entry = teleconEntriesRepo.findByPatientAndReviewersIn(id, reviewId);
        Optional<Patient> patient = patientRepo.findById(id);

        if (entry.isPresent() && patient.isPresent()) {
            return patient.get();
        } else {
            return null;
        }
    }

    public Optional<Patient> getPaitentByIdAndClinicianId(String id, String clinicianId){
        return   patientRepo.findByIdAndClinicianId(new ObjectId(id), new ObjectId(clinicianId));

    }
    public Patient findAndUpdate (String id, String clinicianId , UpdatePatientDto updatePatientDto){
        Optional<Patient> patient =patientRepo.findByIdAndClinicianId(new ObjectId(id), new ObjectId(clinicianId));
        if(patient.isPresent()){
            Patient currentPatient=patient.get();
            currentPatient.setPatientName(updatePatientDto.getPatient_name());
            currentPatient.setGender(updatePatientDto.getGender());

            OffsetDateTime offsetDateTime = OffsetDateTime.parse(updatePatientDto.getDob());
            LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
            currentPatient.setDob(localDateTime);

            currentPatient.setRiskFactors(updatePatientDto.getRisk_factors());
            currentPatient.setHistoDiagnosis(updatePatientDto.getHisto_diagnosis());
            currentPatient.setContactNo(updatePatientDto.getContact_no());
            currentPatient.setSystemicDisease(updatePatientDto.getSystemic_disease());
            currentPatient.setFamilyHistory(updatePatientDto.getFamily_history());
            currentPatient.setMedicalHistory(updatePatientDto.getMedical_history());
            currentPatient.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
            return patientRepo.save(currentPatient);

        }else{
            return null;
        }


    }




public Patient findOne(String patient_id, String clinician_id){
    Patient patient =  patientRepo.findByPatientIdAndClinicianId(patient_id,new ObjectId(clinician_id)).orElse(null);
    return patient;
}
public  Patient findPatient(String id,String clinician_Id){

        ObjectId id_ = new ObjectId(id);
        ObjectId clinicianId_ = new ObjectId(clinician_Id);
        Patient newPatient = patientRepo.findByIdAndClinicianId(id_, clinicianId_).orElse(null);
        return newPatient;
    }

    public ResponseEntity<?> addPatient(
            String id,
            ConsentRequestDto data,
            MultipartFile files) throws IOException {
        List<String> uploadedURIs = new ArrayList<>();

        try {
            Patient patient = findOne(data.getPatient_id(), id);
            if (patient != null) {
                return ResponseEntity.status(401).body("Patient ID already exists");
            }
            String fileName = StringUtils.cleanPath(files.getOriginalFilename());
            try {
                Path path = Paths.get(consentFormUploadDir + File.separator + fileName);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                Files.copy(files.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
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
                newPatient.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
                newPatient.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));


                patientRepo.save(newPatient);
                return ResponseEntity.status(200).body(new ConsentResponseDto(newPatient));
            } catch (MultipartException ex) {
                return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                        "Server Error!", ex.toString()));

            } catch (Exception e) {
                return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                        "Server Error!", e.toString()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                    "Server Error", e.toString()));
        }
    }
}
