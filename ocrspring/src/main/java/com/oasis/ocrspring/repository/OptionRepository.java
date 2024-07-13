package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Option;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OptionRepository extends MongoRepository<Option, String> {
}
