package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.TeleconEntry;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeleconEntriesRepository extends MongoRepository<TeleconEntry, String> {

    Optional<TeleconEntry> findById(ObjectId id);
    Optional<TeleconEntry> findByPatientAndClinicianId(ObjectId patient,ObjectId clinicianId);
    Page<TeleconEntry> findByClinicianId(ObjectId clinicianId, Pageable pageable);
    Page<TeleconEntry> findByClinicianIdAndReviewersIsNotNull(ObjectId clinicianId,Pageable pageable);
    Page<TeleconEntry> findByClinicianIdAndReviewersIsEmpty(ObjectId clinicianId,Pageable pageable);
    Page<TeleconEntry> findByClinicianIdAndReviewsIsNotNull(ObjectId clinicianId,Pageable pageable);
    Page<TeleconEntry> findByClinicianIdAndReviewsIsEmpty(ObjectId clinicianId,Pageable pageable);
    Page<TeleconEntry> findByClinicianIdAndUpdatedTrue(ObjectId clinicianId,Pageable pageable);
    Page<TeleconEntry> findByPatientAndClinicianId(ObjectId patient,ObjectId clinicianId, Pageable pageable);
    Optional<TeleconEntry> findByPatientAndReviewersIn(ObjectId patientId, ObjectId reviewerId);
    Page<TeleconEntry> findByPatient(ObjectId patient, Pageable pageable);
    Optional<TeleconEntry> findByIdAndClinicianId(ObjectId id,ObjectId clinicianId);
    long countByClinicianIdAndUpdatedTrue(ObjectId clinicianId);
    void deleteById(ObjectId id);
    Optional<TeleconEntry> findByIdAndReviewersContaining (ObjectId teleconId, ObjectId reviewerId);

}
