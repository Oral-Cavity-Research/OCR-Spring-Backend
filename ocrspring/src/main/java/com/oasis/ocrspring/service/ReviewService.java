package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Review;
import com.oasis.ocrspring.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;
    public List<Review> allReviewDetails(){

        return reviewRepo.findAll();
    }
}
