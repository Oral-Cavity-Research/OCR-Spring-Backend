package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

}
