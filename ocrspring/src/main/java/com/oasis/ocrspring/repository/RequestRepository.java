package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RequestRepository extends MongoRepository<Request, String> {
    Optional<Request> findByRegNo(String reg_no);

    Optional<Request> findByEmail(String email);
//    Optional<Request> findByRegNo(String regNo);

}
