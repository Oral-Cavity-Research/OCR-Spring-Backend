package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeleconEntriesService {
    @Autowired
    private TeleconEntriesRepository TeleconEntriesRepo;

    public List<TeleconEntry> AllTeleconEntriesDetails(){

        return TeleconEntriesRepo.findAll();
    }
    public TeleconEntry findByID(String id){
        return TeleconEntriesRepo.findById(id).orElse(null);
    }
    public TeleconEntry findOne(String patient_id, String clinician_id){
        TeleconEntry patient =  TeleconEntriesRepo.findByPatientAndClinicianId(patient_id,clinician_id).orElse(null);
        return patient;
    }
    public void save(TeleconEntry teleconEntry){
        TeleconEntriesRepo.save(teleconEntry);
    }
}

