package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
}
