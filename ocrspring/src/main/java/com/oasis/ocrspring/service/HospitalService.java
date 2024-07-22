package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Hospital;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.HospitalRepository;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepo;
    @Autowired
    private HospitalRepository hospitalReop;
    public List<Hospital> AllHospitalDetails(){

        return hospitalRepo.findAll();
    }

}
