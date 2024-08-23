package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.TeleconEntry;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
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
//    @Query(value = "{ '_id': ?0 }", update = "{ '$pull': { 'reviewers': ?1 } }")
//    void pullReviewersFromTeleconEntry(ObjectId entryId,List<ObjectId> reviewerIds);
    void deleteById(ObjectId id);
    Optional<TeleconEntry> findByIdAndReviewersContaining (ObjectId teleconId, ObjectId reviewerId);
}
