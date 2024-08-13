package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Option;
import com.oasis.ocrspring.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepo;

    public List<Option> allOptionDetails() {
        return optionRepo.findAll();
    }
}
