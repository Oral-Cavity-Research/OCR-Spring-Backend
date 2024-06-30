package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    private UserRepository repo;

    public List<User> AllDetails(){
        return repo.findAll();
    }
    public User createUser(User user){
        return repo.save(user);
    }
}
