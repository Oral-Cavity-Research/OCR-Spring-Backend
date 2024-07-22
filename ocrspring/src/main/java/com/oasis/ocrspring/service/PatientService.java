package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReviewRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    public List<User> getReviewer(String id){
//        Optional<TeleconEntry> entry = TeleconEntriesRepo.findByPatientIn(id);
//        List<User> reviewers = null;
//        if (entry.isPresent()) {
//            for (String reviewerId : entry.get().getReviewers()) {
//                Optional<User> reviewer = UserRepo.findById(reviewerId);
//                if (reviewer.isPresent()) {
//                    reviewers.add(reviewer.get());
//                }
//            }
//            return reviewers;
//        }
//        else {
//            return null;
//        }
//    }

}
