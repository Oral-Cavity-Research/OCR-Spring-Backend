package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, String>{
}