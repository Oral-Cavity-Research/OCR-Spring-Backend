package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.TeleconEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeleconEntriesRepository extends MongoRepository<TeleconEntry,String> {

}
