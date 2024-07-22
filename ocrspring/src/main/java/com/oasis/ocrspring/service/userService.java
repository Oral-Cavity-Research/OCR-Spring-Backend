package com.oasis.ocrspring.service;

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
public class userService {
    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private RequestRepository RequestRepo;

    public List<User> AllUserDetails(){

        return UserRepo.findAll();
    }

    public User createUser(User user){
        return UserRepo.save(user);
    }
    public String signup(Request request){
        Optional<User> userByReg_No = UserRepo.findByRegNo(request.getRegNo());
        Optional<User> userByEmail = UserRepo.findByEmail(request.getEmail());
        Optional<Request> requestByReg_No = RequestRepo.findByRegNo(request.getRegNo());
        Optional<Request> requestByEmail = RequestRepo.findByEmail(request.getEmail());

        if(userByReg_No.isPresent()){
            return "User reg no already exist";
        }
        if(userByEmail.isPresent()){
            return "User Email is already registered";
        }
        if (requestByReg_No.isPresent()||requestByEmail.isPresent() ){
            return "A request for registration is already exists";
        }
        RequestRepo.save(request);
        return "Request is sent successfully. You will receive an Email on acceptance";

    }
    public Optional<User> getUserById(String id){

        return UserRepo.findById(id);
    }
    public User updateUser(String id, UserDto userReqBody){
        User user = UserRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setUsername(userReqBody.getUsername());
        user.setHospital(userReqBody.getHospital());
        user.setContact_no(userReqBody.getContact_no());
        user.setAvailability(userReqBody.isAvailability());
        return UserRepo.save(user);

    }
}
