package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshtokenRepsitory extends MongoRepository<RefreshToken,String> {
    Optional<RefreshToken>findByToken(String token);
}
