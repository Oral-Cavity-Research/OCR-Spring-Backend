package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.UpdatePatientDto;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReviewRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    public Optional<Patient> getPaitentByIdAndClinicianId(String id, String clinicianId){
       return   PatientRepo.findByIdAndClinicianId(new ObjectId(id), new ObjectId(clinicianId));

    }
    public Patient findAndUpdate (String id,String clinicianId ,UpdatePatientDto updatePatientDto){
        Optional<Patient> patient =PatientRepo.findByIdAndClinicianId(new ObjectId(id), new ObjectId(clinicianId));
        if(patient.isPresent()){
            Patient currentPatient=patient.get();
            currentPatient.setPatientName(updatePatientDto.getPatientName());
            currentPatient.setGender(updatePatientDto.getGender());
            currentPatient.setDOB(updatePatientDto.getDOB());
            currentPatient.setRiskFactors(updatePatientDto.getRiskFactors());
            currentPatient.setHistoDiagnosis(updatePatientDto.getHistoDiagnosis());
            currentPatient.setContactNo(updatePatientDto.getContactNo());
            currentPatient.setSystemicDisease(updatePatientDto.getSystemicDesease());
            currentPatient.setFamilyHistory(updatePatientDto.getFamilyHistory());
            currentPatient.setMedicalHistory(updatePatientDto.getMedicalHistory());
            return PatientRepo.save(currentPatient);

        }else{
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
