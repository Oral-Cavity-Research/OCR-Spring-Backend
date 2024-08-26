package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.*;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepo;
    private final TeleconEntriesRepository teleconEntriesRepo;
    private final UserRepository userRepo;
    private final TeleconEntriesService teleconServ;

    @Value("${consentFormUploadDir}")
    private String consentFormUploadDir;

    @Autowired
    public PatientService(PatientRepository patientRepo, TeleconEntriesRepository teleconEntriesRepo, UserRepository userRepo, TeleconEntriesService teleconServ) {
        this.patientRepo = patientRepo;
        this.teleconEntriesRepo = teleconEntriesRepo;
        this.userRepo = userRepo;
        this.teleconServ = teleconServ;
    }

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
            LocalDate date = offsetDateTime.toLocalDate();
            currentPatient.setDob(date);

            currentPatient.setRiskFactors(updatePatientDto.getRisk_factors());
            currentPatient.setHistoDiagnosis(updatePatientDto.getHisto_diagnosis());
            currentPatient.setContactNo(updatePatientDto.getContact_no());
            currentPatient.setSystemicDisease(updatePatientDto.getSystemic_disease());
            currentPatient.setFamilyHistory(updatePatientDto.getFamily_history());
            currentPatient.setMedicalHistory(updatePatientDto.getMedical_history());
            currentPatient.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            return patientRepo.save(currentPatient);

        }else{
            return null;
        }


    }




public Patient findOne(String patientId, String clinicianId){
    return patientRepo.findByPatientIdAndClinicianId(patientId,new ObjectId(clinicianId)).orElse(null);
}



public  Patient findPatient(String id,String clinicianId){

        ObjectId idObject = new ObjectId(id);
        ObjectId clinicianIdObject = new ObjectId(clinicianId);
        return patientRepo.findByIdAndClinicianId(idObject, clinicianIdObject).orElse(null);
    }

    public ResponseEntity<?> addPatient(
            String id,
            ConsentRequestDto data,
            MultipartFile files)  {
        List<String> uploadedURIs = new ArrayList<>();

        try {
            Patient patient = findOne(data.getPatientId(), id);
            if (patient != null) {
                return ResponseEntity.status(401).body("Patient ID already exists");
            }

            String originalFilename = files.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("File name cannot be null");
            }
            String fileName = StringUtils.cleanPath(originalFilename);

            return getResponse(data, files, fileName, uploadedURIs);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                    "Server Error", e.toString()));
        }
    }

    private ResponseEntity<?> getResponse(ConsentRequestDto data, MultipartFile files, String fileName, List<String> uploadedURIs) {
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

            newPatient.setPatientId(data.getPatientId());
            newPatient.setClinicianId(new ObjectId(data.getClinicianId()));
            newPatient.setPatientName(data.getPatientName());
            newPatient.setRiskFactors(data.getRiskFactors());
            newPatient.setDob(LocalDate.parse(data.getDob(), DateTimeFormatter.ISO_LOCAL_DATE));
            newPatient.setGender(data.getGender());
            newPatient.setHistoDiagnosis(data.getHistoDiagnosis());
            newPatient.setMedicalHistory(data.getMedicalHistory());
            newPatient.setFamilyHistory(data.getFamilyHistory());
            newPatient.setSystemicDisease(data.getSystemicDisease());
            newPatient.setContactNo(data.getContactNo());
            newPatient.setConsentForm(data.getConsentForm());
            newPatient.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            newPatient.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));


            patientRepo.save(newPatient);
            return ResponseEntity.status(200).body(new ConsentResponseDto(newPatient));
        } catch (MultipartException ex) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                    "Server Error!", ex.toString()));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                    "Server Error!", e.toString()));
        }
    }

    public List<SearchPatientDto> searchPatients(String clinicianId, String searchQuery, int pageQuery, int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(pageQuery - 1, pageSize, sort);
        List<Patient> patientList = patientRepo.findByClinicianIdAndSearch(new ObjectId(clinicianId), searchQuery, pageable);
        List<SearchPatientDto> searchRes = new ArrayList<>();
        for (Patient patient : patientList) {
            searchRes.add(new SearchPatientDto(patient.getId().toString(), patient.getPatientId(), patient.getPatientName(),patient.getDob().toString(), patient.getGender()));
        }
        return searchRes;
    }

    public List<SearchPatientDto> getAllPatients(String clinicianId,int pageQuery,int pageSize,Sort sort){
        Pageable pageable = PageRequest.of(pageQuery-1,pageSize,sort);
        List<Patient>  patientList = patientRepo.findByClinicianId(new ObjectId(clinicianId),pageable);
        List<SearchPatientDto> searchRes = new ArrayList<>();
        for(Patient patient:patientList){
            searchRes.add(new SearchPatientDto(patient.getId().toString(),patient.getPatientId(),patient.getPatientName(),patient.getDob().toString(),patient.getGender()));
        }
        return searchRes;
    }
    public Patient getPatientByPatientIDAndClinicianId(String patientId, String clinicianId){
        return patientRepo.findByPatientIdAndClinicianId(patientId,new ObjectId(clinicianId)).orElse(null);

    }

    public Patient getSharedPatient(String patientId, String reviewerId) {
        Optional<TeleconEntry> entry = teleconEntriesRepo.findByPatientAndReviewersIn(new ObjectId(patientId), new ObjectId(reviewerId));
        Optional<Patient> patient = patientRepo.findById(patientId);

        if (entry.isPresent() && patient.isPresent()) {
            return patient.get();
        } else {
            return null;
        }
    }
}

