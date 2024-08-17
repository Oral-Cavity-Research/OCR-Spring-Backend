package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Review;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.ReviewRepository;
import com.oasis.ocrspring.repository.RoleRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    public List<Review> allReviewDetails(){

        return reviewRepo.findAll();
    }
    public List<User> getAllReviewers() {
        List<String> roles = roleRepo.findByPermissionsIn(Collections.singletonList(200))
                .stream()
                .map(role -> role.getRole())
                .collect(Collectors.toList());
        return userRepo.findByRoleInAndAvailabilityTrue(roles);
    }
}
