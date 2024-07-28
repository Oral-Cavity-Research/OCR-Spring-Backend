package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.TeleconEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeleconEntriesRepository extends MongoRepository<TeleconEntry,String> {

    Optional<TeleconEntry> findByPatientAndReviewersIn(String patient, String reviewerId);

    Optional<TeleconEntry> findByPatientIn(String patient);
    Optional<TeleconEntry> findByPatientAndClinicianId(String patient,String clinicianId);
}
