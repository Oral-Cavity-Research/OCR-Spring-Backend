package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
