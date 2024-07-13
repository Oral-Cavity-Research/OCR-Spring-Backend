package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HospitalRepository extends MongoRepository<Hospital, String>{
}
