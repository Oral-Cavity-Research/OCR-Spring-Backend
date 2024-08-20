package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    long countByReviewerIdAndReviewedFalse(ObjectId clinicianId);
    void deleteByTeleconEntryAndReviewerId(ObjectId teleconEntry, ObjectId reviewerId);
}
