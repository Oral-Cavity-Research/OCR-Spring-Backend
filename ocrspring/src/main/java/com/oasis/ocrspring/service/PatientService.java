package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository PatientRepo;
    public List<Patient> AllPatientDetails(){

        return PatientRepo.findAll();
    }
    public Patient createPatient(Patient patient){

        return PatientRepo.save(patient);
    }
}
