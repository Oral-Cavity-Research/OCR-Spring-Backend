package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RequestRepository;
import com.oasis.ocrspring.repository.UserRepository;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
import com.oasis.ocrspring.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepo;
    public List<Request> AllRequestDetails(){
        return requestRepo.findAll();
    }
     public Optional<Request> getRequestById(String id) {
         return requestRepo.findById(id);
     }
    public Request createRequest(Request Request){
        return requestRepo.save(Request);
    }
    public boolean rejectRequest(String id, String reason) throws ErrorMessage {
        Optional<Request> requestOptional = requestRepo.findById(id);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            requestRepo.deleteById(id);
            emailService.sendEmail(request.getEmail(), "REJECT", reason, request.getUserName());
            return true;
        } else {
            return false;
        }
    }
    public void acceptRequest(String id, User newUser, String reason) throws ErrorMessage {
        Optional<Request> requestOptional = requestRepo.findById(id);
            Request request = requestOptional.get();
            userRepo.save(newUser);
            requestRepo.deleteById(id);
            emailService.sendEmail(request.getEmail(), "ACCEPT", reason, request.getUserName());


    }
}
