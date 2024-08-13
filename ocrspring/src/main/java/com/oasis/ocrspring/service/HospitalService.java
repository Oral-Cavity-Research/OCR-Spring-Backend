package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Hospital;
import com.oasis.ocrspring.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepo;
    @Autowired
    private HospitalRepository hospitalReop;

    public List<Hospital> allHospitalDetails() {
        return hospitalRepo.findAll();
    }

}
