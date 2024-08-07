package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRole(String role);
}
