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

    public Patient findOne(String patientId, String clinicianId) {
        Patient patient = patientRepo.findByPatientIdAndClinicianId(patientId, new ObjectId(clinicianId)).orElse(null);
        return patient;
    }

    public Patient findPatient(String id, String clinicianId) {
        ObjectId id_ = new ObjectId(id);
        ObjectId clinicianId_ = new ObjectId(clinicianId);
        Patient newPatient = patientRepo.findByIdAndClinicianId(id_, clinicianId_).orElse(null);
        return newPatient;
    }

    public ResponseEntity<?> addPatient(
            String id,
            ConsentRequestDto data,
            MultipartFile files) throws IOException {
        List<String> uploadedURIs = new ArrayList<>();

        try {
            Patient patient = findOne(data.getPatientId(), id);
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
                newPatient.setPatientId(data.getPatientId());
                newPatient.setClinicianId(new ObjectId(data.getClinicianId()));
                newPatient.setPatientName(data.getPatientName());
                newPatient.setRiskFactors(data.getRiskFactors());
                newPatient.setHistoDiagnosis(data.getHistoDiagnosis());
                newPatient.setMedicalHistory(data.getMedicalHistory());
                newPatient.setFamilyHistory(data.getFamilyHistory());
                newPatient.setSystemicDisease(data.getSystemicDisease());
                newPatient.setContactNo(data.getContactNo());
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

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal " +
                    "Server Error", e.toString()));
        }
    }
}
