package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    private UserRepository UserRepo;

    public List<User> AllUserDetails(){

        return UserRepo.findAll();
    }

    public User createUser(User user){
        return UserRepo.save(user);
    }
}
