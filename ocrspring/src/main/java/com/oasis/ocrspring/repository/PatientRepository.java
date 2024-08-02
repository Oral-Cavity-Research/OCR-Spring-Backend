package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
   Optional<Patient> findByIdAndClinicianId(ObjectId id, ObjectId clinicianId);
}
