package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.*;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeleconEntriesService {
    @Autowired
    private TeleconEntriesRepository teleconEntriesRepo;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private UserRepository userRepo;

    public List<TeleconEntry> allTeleconEntriesDetails() {
        return teleconEntriesRepo.findAll();
    }

    public TeleconEntry findByID(String id) {
        return teleconEntriesRepo.findById(id).orElse(null);
    }

    public TeleconEntry findOne(String patientId_, String clinicianId_) {
        ObjectId patientId = new ObjectId(patientId_);
        ObjectId clinicianId = new ObjectId(clinicianId_);
        TeleconEntry patient = teleconEntriesRepo.findByPatientAndClinicianId(patientId, clinicianId).orElse(null);
        return patient;
    }

    public void save(TeleconEntry teleconEntry) {
        teleconEntriesRepo.save(teleconEntry);
    }

    public ResponseEntity<?> patientTeleconEntry(String patientId,
                                                 String clinicianId,
                                                 PatientTeleconRequest newPatient) { //path patient id

        try {
            Patient patient = patientService.findPatient(patientId, clinicianId);//patient_id, String clinician_id
            if (patient != null) {
                TeleconEntry newEntry = new TeleconEntry();
                newEntry.setPatient(patient.getId());
                newEntry.setClinicianId(patient.getClinicianId());
                newEntry.setStartTime(LocalDateTime.parse(newPatient.getStartTime(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                newEntry.setEndTime(LocalDateTime.parse(newPatient.getEndTime(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                newEntry.setComplaint(newPatient.getComplaint());
                newEntry.setFindings(newPatient.getFindings());
                newEntry.setCurrentHabits(newPatient.getCurrentHabits());
                newEntry.setReviewers(new ArrayList<>());
                newEntry.setReviews(new ArrayList<>());
                newEntry.setImages(new ArrayList<>());
                newEntry.setReports(new ArrayList<>());
                newEntry.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                newEntry.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

                try {
                    teleconEntriesRepo.save(newEntry);
                    return ResponseEntity.status(200).body(new PatientTeleconResponse(newEntry));
                } catch (Exception ex) {
                    return ResponseEntity.status(500).body(new MessageDto("Tele consultation entry failed"));
                }
            } else {
                return ResponseEntity.status(404).body(new MessageDto("Patient is not registered"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!", e.toString()));
        }

    }

    public ResponseEntity<?> getAllUserEntries(String id, Integer page, String filter, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, getSortField(filter)));//page-1 cuz Page numbers in Spring Data are zero-based
        //pageSize is the number of items you want to retrieve per page
        ObjectId id_ = new ObjectId(id);
        Page<TeleconEntry> entryPage;
        List<TeleconEntry> entryPageList;
        try {
            entryPage = switch (filter) {
                case "Assigned" -> teleconEntriesRepo.findByClinicianIdAndReviewersIsNotNull(id_, pageable);
                case "Unassigned" -> teleconEntriesRepo.findByClinicianIdAndReviewersIsEmpty(id_, pageable);
                case "Reviewed" -> teleconEntriesRepo.findByClinicianIdAndReviewsIsNotNull(id_, pageable);
                case "Unreviewed" -> teleconEntriesRepo.findByClinicianIdAndReviewsIsEmpty(id_, pageable);
                case "Newly Reviewed" -> teleconEntriesRepo.findByClinicianIdAndUpdatedTrue(id_, pageable);
                default -> teleconEntriesRepo.findByClinicianId(id_, pageable);
            };
            entryPageList = entryPage.getContent();
            List<TeleconEntryDto> response = new ArrayList<>();

            //loop through the list to access getters and setters
            for (TeleconEntry entry : entryPageList) {
                Patient patient = patientRepo.findById(entry.getPatient());
                PatientDetailsDto patientDetails = new PatientDetailsDto(patient);
                List<String> reviewerList = entry.getReviewers();
                List<ReviewerDetailsDto> reviewerObjectList = new ArrayList<>();
                for (String Reviewer : reviewerList) {
                    User user = userRepo.findById(new ObjectId(Reviewer));
                    ReviewerDetailsDto reviewerDetails = new ReviewerDetailsDto(user);
                    reviewerObjectList.add(reviewerDetails);
                }
                response.add(new TeleconEntryDto(entry, patientDetails, reviewerObjectList));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("Internal Server Error!", e.toString()));
        }

    }

    private String getSortField(String filter) {
        if (filter.equals("Updated At")) {
            return "updatedAt";
        }
        return "createdAt";
    }
}

