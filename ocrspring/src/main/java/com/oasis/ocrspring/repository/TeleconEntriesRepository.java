package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.TeleconEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeleconEntriesRepository extends MongoRepository<TeleconEntry,String> {

    Optional<TeleconEntry> findByPatientAndReviewersIn(String patient, String reviewerId);
}
