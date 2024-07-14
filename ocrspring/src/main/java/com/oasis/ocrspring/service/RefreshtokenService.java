package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.RefreshToken;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.RefreshtokenRepsitory;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RefreshtokenService {
    @Autowired
    private RefreshtokenRepsitory refreshtokenRepo;
    public List<RefreshToken> AllRefreshtokenDetails(){
        System.out.println("appeared in service layer");
        return refreshtokenRepo.findAll();
    }
}
