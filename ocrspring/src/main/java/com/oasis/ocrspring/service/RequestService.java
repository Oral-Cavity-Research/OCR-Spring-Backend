package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RequestService {
    @Autowired
    private RequestRepository RequestRepo;
    public List<Request> AllRequestDetails(){
        System.out.println("appeared in service layer");
        return RequestRepo.findAll();
    }
    public Request createRequest(Request Request){
        return RequestRepo.save(Request);
    }
}
