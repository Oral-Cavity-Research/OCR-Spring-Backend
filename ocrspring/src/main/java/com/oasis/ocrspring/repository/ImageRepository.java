package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}
