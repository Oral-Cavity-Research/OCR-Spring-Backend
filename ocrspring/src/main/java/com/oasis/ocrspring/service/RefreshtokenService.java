package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.RefreshToken;
import com.oasis.ocrspring.repository.RefreshtokenRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefreshtokenService {
    @Autowired
    private RefreshtokenRepsitory refreshtokenRepo;

    public List<RefreshToken> allRefreshtokenDetails() {
        System.out.println("appeared in service layer");
        return refreshtokenRepo.findAll();
    }
}
