package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Review;
import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.repository.ReviewRepository;
import com.oasis.ocrspring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.List;
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository ReviewRepo;
    public List<Review> AllReviewDetails(){

        return ReviewRepo.findAll();
    }
}
