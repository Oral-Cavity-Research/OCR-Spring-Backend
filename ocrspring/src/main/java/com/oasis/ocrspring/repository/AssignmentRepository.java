package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
}
