package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.RequestDto;
import com.oasis.ocrspring.dto.UserDto;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RequestRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RequestRepository requestRepo;


    public List<User> allUserDetails(){

        return userRepo.findAll();
    }

    public User createUser(User user){
        return userRepo.save(user);
    }
    public String signup(RequestDto request){
        Optional<User> userRepoByRegNo = userRepo.findByRegNo(request.getReg_no());
        Optional<User> userByEmail = userRepo.findByEmail(request.getEmail());
        Optional<Request> requestByRegNo = requestRepo.findByRegNo(request.getReg_no());
        Optional<Request> requestByEmail = requestRepo.findByEmail(request.getEmail());

        if(userRepoByRegNo.isPresent()){
            return "User reg no already exist";
        }
        if(userByEmail.isPresent()){
            return "User Email is already registered";
        }
        if (requestByRegNo.isPresent()||requestByEmail.isPresent() ){
            return "A request for registration is already exists";
        }

        requestRepo.save(new Request(request.getUsername(),request.getEmail(),request.getReg_no(),request.getHospital(),request.getDesignation(),request.getContact_no()));
        return "Request is sent successfully. You will receive an Email on acceptance";

    }
    public Optional<User> getUserById(String id){

        return userRepo.findById(id);
    }

    public Optional<User> getUserByEmail(String id){

        return userRepo.findByEmail(id);
    }
    public User updateUser(String id, UserDto userReqBody){
        User user = userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setUsername(userReqBody.getUsername());
        user.setHospital(userReqBody.getHospital());
        user.setContactNo(userReqBody.getContactNo());
        user.setAvailability(userReqBody.isAvailability());
        return userRepo.save(user);

    }
}
