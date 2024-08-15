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
    private TeleconEntriesRepository TeleconEntriesRepo;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private UserRepository userRepo;

    public List<TeleconEntry> AllTeleconEntriesDetails(){

        return TeleconEntriesRepo.findAll();
    }
    public TeleconEntry findByID(String id){
        return TeleconEntriesRepo.findById(id).orElse(null);
    }
    public TeleconEntry findOne(String patient_id, String clinician_id){
        ObjectId patientId = new ObjectId(patient_id);
        ObjectId clinicianId = new ObjectId(clinician_id);
        TeleconEntry patient =  TeleconEntriesRepo.findByPatientAndClinicianId(patientId,clinicianId).orElse(null);
        return patient;
    }
    public void save(TeleconEntry teleconEntry){
        TeleconEntriesRepo.save(teleconEntry);
    }
    public ResponseEntity<?> patientTeleconEntry(String patient_id,
                                                 String clinician_id,
                                                 PatientTeleconRequest newPatient ){ //path patient id

        try{
            Patient patient = patientService.findPatient(patient_id,clinician_id);//patient_id, String clinician_id
            if(patient != null){
                TeleconEntry newEntry = new TeleconEntry();
                newEntry.setPatient(patient.getId());
                newEntry.setClinicianId(patient.getClinicianId());
                newEntry.setStartTime(newPatient.getStartTime());
                newEntry.setEndTime(newPatient.getEndTime());
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
                    TeleconEntriesRepo.save(newEntry);
                    return ResponseEntity.status(200).body(new PatientTeleconResponse(newEntry));
                }
                catch(Exception ex){
                    return ResponseEntity.status(500).body(new MessageDto("Tele consultation entry failed"));
                }
            }
            else{
                return ResponseEntity.status(404).body(new MessageDto("Patient is not registered" ));
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }

    }

    public ResponseEntity<?> getAllUserEntries(String id, Integer page, String filter,Integer pageSize)
    {
        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Direction.DESC,getSortField(filter)));//page-1 cuz Page numbers in Spring Data are zero-based
        //pageSize is the number of items you want to retrieve per page
        ObjectId id_ = new ObjectId(id);
        Page<TeleconEntry> EntryPage;
        List<TeleconEntry> entryPageList ;
        //Page<TeleconEntryDto> userDetailList;
        try {
            switch (filter) {
                case "Assigned":
                    EntryPage = TeleconEntriesRepo.findByClinicianIdAndReviewersIsNotNull(id_, pageable);
                    break;
                case "Unassigned":
                    EntryPage = TeleconEntriesRepo.findByClinicianIdAndReviewersIsEmpty(id_, pageable);
                    break;
                case "Reviewed":
                    EntryPage = TeleconEntriesRepo.findByClinicianIdAndReviewsIsNotNull(id_, pageable);
                    break;
                case "Unreviewed":
                    EntryPage = TeleconEntriesRepo.findByClinicianIdAndReviewsIsEmpty(id_, pageable);
                    break;
                case "Newly Reviewed":
                    EntryPage = TeleconEntriesRepo.findByClinicianIdAndUpdatedTrue(id_, pageable);
                    break;
                default:
                    EntryPage = TeleconEntriesRepo.findByClinicianId(id_, pageable);
                    break;
            }
            entryPageList = EntryPage.getContent();
            List<TeleconEntryDto> response = new ArrayList<>();

            //loop through the list to access getters and setters
            for(TeleconEntry entry: entryPageList){
                Patient patient = patientRepo.findById(entry.getPatient());
                patientDetailsDto patientDetails =new patientDetailsDto(patient);
                List<ObjectId> ReviewerList = entry.getReviewers() ;
                List<ReviewerDetailsDto> reviewerObjectList = new ArrayList<>();
                for(ObjectId Reviewer : ReviewerList){
                    User user = userRepo.findById(Reviewer);
                    ReviewerDetailsDto reviewerDetails = new ReviewerDetailsDto(user);
                    reviewerObjectList.add(reviewerDetails);
                }
                response.add(new TeleconEntryDto(entry,patientDetails,reviewerObjectList));
            }
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }

    }
    public ResponseEntity<?> getUserEntryById(String clinicianId,String patient,
                                              Integer page, String filter, Integer pageSize){
        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Direction.DESC,getSortField(filter)));
        ObjectId patientId = new ObjectId(patient);
        ObjectId clinician_Id = new ObjectId(clinicianId);
        Page<TeleconEntry> EntryPage ;
        List<TeleconEntry> EntryPageList;
        List<TeleconEntryDto> response = new ArrayList<>();
        try{
            EntryPage = TeleconEntriesRepo.findByPatientAndClinicianId(patientId,clinician_Id,pageable);
            EntryPageList = EntryPage.getContent();

            for(TeleconEntry entry:EntryPageList){
//                List<ObjectId> reviewerIdList = new ArrayList<>();
                Patient patientProfile = patientRepo.findById(entry.getPatient());
                patientDetailsDto patientDetails = new patientDetailsDto(patientProfile);
                List<ObjectId> reviewerIdList = entry.getReviewers();
                List<ReviewerDetailsDto> reviewerList = new ArrayList<>();
                for (ObjectId reviewer: reviewerIdList){
                    User Reviewer = userRepo.findById(reviewer);
                    reviewerList.add(new ReviewerDetailsDto(Reviewer));
                }
                response.add(new TeleconEntryDto(entry,patientDetails,reviewerList));
            }

            return  ResponseEntity.status(200).body(response);
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }

    private String getSortField(String filter){
        if(filter.equals("Updated At")){
            return "updatedAt";
        }
        return "createdAt";
    }
}

