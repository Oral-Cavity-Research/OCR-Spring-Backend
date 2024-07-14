package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Option;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.OptionRepository;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepo;
    public List<Option> AllOptionDetails(){
        System.out.println("appeared in service layer");
        return optionRepo.findAll();
    }
}
