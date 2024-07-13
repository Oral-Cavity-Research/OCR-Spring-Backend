package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {


}
