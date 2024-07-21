package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByRegNo(String regNo);
    Optional<User>  findByEmail(String email);

}
