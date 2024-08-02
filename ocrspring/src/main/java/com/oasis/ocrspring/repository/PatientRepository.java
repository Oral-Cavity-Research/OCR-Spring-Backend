package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Patient;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientRepository extends MongoRepository<Patient, String> {

    @Aggregation(pipeline = {
        "{ '$unwind': '$risk_factors' }",
        "{ '$group': { '_id': '$risk_factors.habit', 'count': { '$sum': 1 } } }",
        "{ '$project': { '_id': 0, 'habit': '$_id', 'count': 1 } }"
    })
    List<RiskFactorCount> getRiskFactors();
}
