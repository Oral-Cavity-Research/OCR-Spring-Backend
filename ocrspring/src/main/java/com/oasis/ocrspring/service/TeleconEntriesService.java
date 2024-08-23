package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.*;
import com.oasis.ocrspring.model.*;
import com.oasis.ocrspring.repository.*;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    private AssignmentRepository assignmentRepo;
    @Autowired
    private ImageRepository imageRepo;
    @Autowired
    private ReportRepository reportRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ReviewRepository reviewRepo;

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
                newEntry.setStartTime(OffsetDateTime.parse(newPatient.getStartTime()).toLocalDateTime());
                newEntry.setEndTime(OffsetDateTime.parse(newPatient.getEndTime()).toLocalDateTime());
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
                Patient patient = (patientRepo.findById(entry.getPatient()).orElse(null));
                PatientDetailsDto patientDetails =new PatientDetailsDto(patient);
                List<ObjectId> ReviewerList = entry.getReviewers() ;
                List<ReviewerDetailsDto> reviewerObjectList = new ArrayList<>();
                for(ObjectId Reviewer : ReviewerList){
                    User user = userRepo.findById(Reviewer).orElse(null);
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
                Patient patientProfile = patientRepo.findById(entry.getPatient()).orElse(null);
                PatientDetailsDto patientDetails = new PatientDetailsDto(patientProfile);
                List<ObjectId> reviewerIdList = entry.getReviewers();
                List<ReviewerDetailsDto> reviewerList = new ArrayList<>();
                for (ObjectId reviewer: reviewerIdList){
                    User Reviewer = userRepo.findById(reviewer).orElse(null);
                    reviewerList.add(new ReviewerDetailsDto(Reviewer));
                }
                response.add(new TeleconEntryDto(entry,patientDetails,reviewerList));
            }
            return  ResponseEntity.status(200).body(response);
        } catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }

    public ResponseEntity<?> getSharedPatient(String clinicianId, String patient,String filter,Integer pageSize,Integer page){

        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by( Sort.Direction.DESC, getSortField(filter)));
        ObjectId patientId = new ObjectId(patient);
        ObjectId clinician_id = new ObjectId(clinicianId);
        List<TeleconEntry> entryList = new ArrayList<>();
        List<TeleconEntryDto> results = new ArrayList<>();

        try {
            Optional<TeleconEntry> entry = TeleconEntriesRepo.findByPatientAndReviewersIn(patientId, clinician_id); //List Can be used as well
            if (entry.isEmpty()) {
                return ResponseEntity.status(404).body(new ErrorMessage("Entries Not Found"));
            }
            Page<TeleconEntry> entries = TeleconEntriesRepo.findByPatient(patientId, pageable);
            entryList = entries.getContent();
            for(TeleconEntry element: entryList){
                Patient newPatient = patientRepo.findById(element.getPatient()).orElse(null);
                PatientDetailsDto patientDetails = new PatientDetailsDto(newPatient);
                List<ObjectId> reviewerList = element.getReviewers();
                List<ReviewerDetailsDto> reviewerDetails = new ArrayList<>();
                setReviewerDetails(reviewerList, reviewerDetails);
                results.add(new TeleconEntryDto(element,patientDetails,reviewerDetails));
            }
            return ResponseEntity.status(200).body(results);
        }catch(NullPointerException er){
            return ResponseEntity.status(404).body(new ErrorMessage("Entries Not Found"));
        } catch (Exception e ){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }


    }
    public ResponseEntity<?> getEntryDetails(String clinicianId,String id){
        ObjectId clinicinId_ = new ObjectId(clinicianId);
        ObjectId teleconId = new ObjectId(id);
        try{
            Optional<TeleconEntry> entry = TeleconEntriesRepo.findByIdAndClinicianId(teleconId,clinicinId_);
            if (entry.isPresent()) {
                TeleconEntry entryDetails = entry.get();
                PopulatedResultDto teleconDetails = new PopulatedResultDto();

                Patient patient = patientRepo.findById(entryDetails.getPatient()).orElse(null);
                PatientDetailsDto patientDetails = new PatientDetailsDto(patient);

                List<ObjectId> reviewers = entryDetails.getReviewers();
                List<ReviewerDetailsDto> reviewerDetailList = new ArrayList<>();
                List<Image> imageList = new ArrayList<>();
                List<Report> reportList = new ArrayList<>();

                setReviewerDetails(reviewers, reviewerDetailList);
                setImageDetails(entryDetails.getImages(),imageList);
                setReportDetails(entryDetails.getReports(),reportList);

                teleconDetails = new PopulatedResultDto(entryDetails, patientDetails, reviewerDetailList,imageList,reportList);
                return ResponseEntity.status(200).body(teleconDetails);
            }else {
                return ResponseEntity.status(404).body(new ErrorMessage("Entry not found"));
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }
    public ResponseEntity<?> countNewReviews(String clinicianId){
        ObjectId clinicianId_ = new ObjectId(clinicianId);
        try{
            long count = TeleconEntriesRepo.countByClinicianIdAndUpdatedTrue(clinicianId_);
            Map<String,Long> response = new HashMap<>();
            response.put("count",count);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }

    }

    public ResponseEntity<?> addReviewer(String clinicianId, String id, String reviewerId){

        ObjectId clinicianId_ = new ObjectId(clinicianId);
        ObjectId teleconId = new ObjectId(id);
        ObjectId reviewerId_ = new ObjectId(reviewerId);

        Optional<TeleconEntry> entry;
        TeleconEntry entryElement;
        List<ObjectId> reviewers;
        try {
            entry = TeleconEntriesRepo.findByIdAndClinicianId(teleconId, clinicianId_);
            entryElement = entry.get();
            reviewers = entryElement.getReviewers();
            if(entry.isEmpty()){
                return ResponseEntity.status(404).body(new ErrorMessage("Entry not found"));
            }
            if (entryElement.getReviewers().contains(reviewerId_)){

                TeleconEntry teleconEntry = TeleconEntriesRepo.findByIdAndClinicianId(teleconId,clinicianId_).get();
                List<ObjectId> reviewersIdList = teleconEntry.getReviewers();
                List<ObjectId> imageIdList = teleconEntry.getImages();
                List<ObjectId> reportIdList = teleconEntry.getReports();

                Patient patient = patientRepo.findById(teleconEntry.getPatient()).orElse(null);
                PatientDetailsDto patientDetails = new PatientDetailsDto(patient);

                List<ReviewerDetailsDto> reviewerDetails = new ArrayList<>();
                List<Image> imageList = new ArrayList<>();
                List<Report> reportList = new ArrayList<>();

                setReviewerDetails(reviewersIdList, reviewerDetails);
                setImageDetails(imageIdList, imageList);
                setReportDetails(reportIdList, reportList);

                PopulatedResultDto updatedEntry = new PopulatedResultDto(teleconEntry,patientDetails,reviewerDetails,imageList,reportList);
                return ResponseEntity.status(200).body(updatedEntry);
            }
            createAssignment(teleconId, clinicianId_);

            reviewers.add(reviewerId_);
            entryElement.setReviewers(reviewers);
            TeleconEntriesRepo.save(entryElement);
            return ResponseEntity.status(200).body(new MessageDto("Reviewer is added" ));

        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }

    public ResponseEntity<?> removeReviewer(String clinicianId,String id, String reviewerId){

        ObjectId clinicianId_ = new ObjectId(clinicianId);
        ObjectId teleconId = new ObjectId(id);
        ObjectId reviewerId_ = new ObjectId(reviewerId);

        Optional<TeleconEntry> entry;
        TeleconEntry entryElement;
        List<ObjectId> reviewers;
        try{
            entry = TeleconEntriesRepo.findByIdAndClinicianId(teleconId,clinicianId_);
            entryElement = entry.get();
            if (entry.isEmpty()){
                return ResponseEntity.status(404).body(new MessageDto("Entry is not found"));
            }
            assignmentRepo.deleteByTeleconEntryAndReviewerId(teleconId,reviewerId_);
            pullReviewFromEntry(teleconId,entryElement.getReviewers());
            return ResponseEntity.status(200).body(new MessageDto("Reviewer is removed"));
        }catch(Exception err){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!" ,err.toString()));
        }
    }
    public ResponseEntity<?> deleteEntry(String clinicianId,String id){
        ObjectId clinicianId_ = new ObjectId(clinicianId);
        ObjectId teleconId_ = new ObjectId(id);
        try{
            Optional<TeleconEntry> entry = TeleconEntriesRepo.findByIdAndClinicianId(teleconId_,clinicianId_);
            TeleconEntry entry_ = entry.get();
            final LocalDateTime now = LocalDateTime.now();
            final LocalDateTime createdAt = entry_.getCreatedAt();
            final Duration duration = Duration.between(now,createdAt) ;
            final float hours = Duration.ZERO.toMinutes()/60f;
            if(hours >= 24){
                return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
            }
            if (entry.isPresent()){
                assignmentRepo.deleteByTeleconEntry(teleconId_);
                imageRepo.deleteByTeleconEntryId(teleconId_);
                reportRepo.deleteByTeleconId(teleconId_);
                TeleconEntriesRepo.deleteById(teleconId_);
                return ResponseEntity.status(200).body(new MessageDto("Entry is deleted successfully"));
            }
            else {
                return ResponseEntity.status(404).body(new MessageDto("Entry Not Found!"));
            }
        }catch (Exception err){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",err.toString()));
        }
    }

    public ResponseEntity<?> getAllSharedEntries(int page, int pageSize, String clinicianId,String filter) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("reviewer_id", clinicianId);
        if (filter != null && !filter.equals("All")) {
            filterMap.put("reviewed", filter.equals("Reviewed"));
        }
        try {
            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Assignment> assignments;
            if (filterMap.containsKey("reviewed")) {
                assignments = assignmentRepo.findByReviewerIdAndReviewed((ObjectId) filterMap.get("reviewer_id"), (Boolean) filterMap.get("reviewed"), pageable);
            } else {
                assignments = assignmentRepo.findByReviewerId(new ObjectId(clinicianId), pageable);
            }

            List<SharedEntriesDto> results = new ArrayList<>();

            if (!assignments.isEmpty()) {
                for (Assignment assignment : assignments) {
                    ObjectId teleconId = assignment.getTeleconEntry();
                    TeleconEntry teleconEntry = TeleconEntriesRepo.findById(teleconId).orElse(null);
                    if (teleconEntry == null) {
                        results.add(new SharedEntriesDto(null, null, null, assignment));
                    }
                    else {
                        ObjectId patientId = teleconEntry.getPatient();
                        ObjectId clicianId = teleconEntry.getClinicianId();
                        Patient patient = patientRepo.findById(patientId).orElse(null);
                        User clinician = userRepo.findById(clicianId).orElse(null);
                        SharedEntriesDto result = new SharedEntriesDto(teleconEntry, patient, clinician, assignment);
                        results.add(result);

                    }
                }

            }
            return ResponseEntity.status(200).body(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }

    public ResponseEntity<?> getSharedEntry(String id, String clinicianId){
        ObjectId teleconId = new ObjectId(id);
        ObjectId clinicianId_ = new ObjectId(clinicianId);
        try{
            TeleconEntry entry = TeleconEntriesRepo.findByIdAndReviewersContaining(teleconId,clinicianId_).orElse(null);
            if (entry == null){
                return ResponseEntity.status(404).body(new MessageDto("Entry Not Found!"));
            }
            Patient patient = patientRepo.findById(entry.getPatient()).orElse(null);
            List<ObjectId> reviewerList = entry.getReviewers();
            List<ObjectId> imageIdList = entry.getImages();
            List<ObjectId> reportIdList = entry.getReports();

            List<ReviewerDetailsDto_> reviewerDetails = new ArrayList<>();
            List<Image> imageDetailsList = new ArrayList<>();
            List<Report> reportDetailsList = new ArrayList<>();

            setReviewers(reviewerList, reviewerDetails);
            setImageDetails( imageIdList, imageDetailsList);
            setReportDetails(reportIdList, reportDetailsList);
            PatientDetailsDto patientDetails = new PatientDetailsDto(patient);

            PopulatedEntryDto result = new PopulatedEntryDto(entry,patientDetails,reviewerDetails,imageDetailsList,reportDetailsList);
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }

    }

    public ResponseEntity<?> getAssignedEntryDetails(String id){
        ObjectId assignmentId = new ObjectId(id);
        try{
            Assignment assignment = assignmentRepo.findById(assignmentId).orElse(null);
            AssignedEntryDetailsDto result = new AssignedEntryDetailsDto();
            if (assignment != null){
                TeleconEntry entry = TeleconEntriesRepo.findById(assignment.getTeleconEntry()).orElse(null);
                if(entry != null){
                    List<ObjectId> imageIdList = entry.getImages();
                    List<ObjectId> reportIdList = entry.getReports();

                    List<Image> imageDetailsList = new ArrayList<>();
                    List<Report> reportDetailsList = new ArrayList<>();

                    setImageDetails( imageIdList, imageDetailsList);
                    setReportDetails(reportIdList, reportDetailsList);
                    Patient patient = patientRepo.findById(entry.getPatient()).orElse(null);
                    User clinician = userRepo.findById(entry.getClinicianId()).orElse(null);
                    PatientDetailsDto patientDetails = new PatientDetailsDto(patient);
                    ClinicianDetailsDto clinicianDetails = new ClinicianDetailsDto(clinician);

                    result = new AssignedEntryDetailsDto(entry, patientDetails, clinicianDetails,
                            imageDetailsList, reportDetailsList);

                    result.setAssignedAt(assignment.getCreatedAt());
                    result.setReviewed(assignment.getReviewed());
                    result.setChecked(assignment.getChecked());
                }
                return ResponseEntity.status(200).body(result);
            }else {
                return ResponseEntity.status(404).body(new MessageDto("Entry Not Found!"));
            }

        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }

    public ResponseEntity<?> getEntryReviews(String id){
        ObjectId teleconId = new ObjectId(id);
        try{
            List<Review> reviews = reviewRepo.findByTeleconEntryId(teleconId);
            List<ReviewDetailsDto> reviewDetails = new ArrayList<>();
            if (!reviews.isEmpty()){
                for(Review review: reviews){
                    User reviewer = userRepo.findById(review.getReviewerId()).orElse(null);
                    reviewDetails.add(new ReviewDetailsDto(review,reviewer));
                }
                return ResponseEntity.status(200).body(reviewDetails);
            }else {
                return ResponseEntity.status(404).body(new MessageDto("Entry Not Found!"));
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ErrorResponseDto("Internal Server Error!",e.toString()));
        }
    }
    public void pullReviewFromEntry(ObjectId teleconId, List<ObjectId> reviewers){
        Query query = new Query().addCriteria(Criteria.where("_id").is(teleconId)) ;
        Update update = new Update().pullAll("reviewers", reviewers.toArray());
        mongoTemplate.updateFirst(query,update,TeleconEntry.class);
    }

    private void createAssignment(ObjectId teleconId, ObjectId clinicianId_) {
        Assignment newAssignement = new Assignment();
        newAssignement.setTeleconEntry(teleconId);
        newAssignement.setReviewerId(clinicianId_);
        newAssignement.setChecked(Boolean.FALSE);
        newAssignement.setReviewed(Boolean.FALSE);
        assignmentRepo.save(newAssignement);
    }

    private void setReportDetails(List<ObjectId> reportIdList, List<Report> reportList) {
        if(!reportIdList.isEmpty()){
            for (ObjectId report: reportIdList){
                Report reportObject = reportRepo.findById(report);
                reportList.add(reportObject);
            }
        }
    }

    private void setImageDetails(List<ObjectId> imageIdList, List<Image> imageList) {
        if(!imageIdList.isEmpty()){
            for (ObjectId imageId: imageIdList){
                Image imageObject = imageRepo.findById(imageId);
                imageList.add(imageObject);
            }
        }
    }

    private void setReviewerDetails(List<ObjectId> reviewersIdList, List<ReviewerDetailsDto> reviewerDetails) {
        if(!reviewersIdList.isEmpty()){
            for (ObjectId reviewer: reviewersIdList){
                User reviewerObject = userRepo.findById(reviewer).orElse(null);
                reviewerDetails.add(new ReviewerDetailsDto(reviewerObject));
            }
        }
    }
    private void setReviewers(List<ObjectId> reviwerIdList,List<ReviewerDetailsDto_> reviewerDetails){
        if (!reviwerIdList.isEmpty()){
            for(ObjectId reviewer: reviwerIdList){
                User reviewerObject = userRepo.findById(reviewer).orElse(null);
                reviewerDetails.add(new ReviewerDetailsDto_(reviewerObject));
            }
        }
    }

    private String getSortField(String filter){
        if(filter.equals("Updated At")){
            return "updatedAt";
        }
        return "createdAt";
    }
}

