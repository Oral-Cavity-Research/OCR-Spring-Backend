package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshtokenRepsitory extends MongoRepository<RefreshToken,String> {
}
