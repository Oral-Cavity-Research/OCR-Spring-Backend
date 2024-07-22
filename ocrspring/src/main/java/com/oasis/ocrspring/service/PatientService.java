package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReviewRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository PatientRepo;

    @Autowired
    private TeleconEntriesRepository TeleconEntriesRepo;

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

        return PatientRepo.findById(id).orElse(null);
    }
}
