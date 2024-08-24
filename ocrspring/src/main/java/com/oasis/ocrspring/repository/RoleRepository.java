package com.oasis.ocrspring.repository;

import com.oasis.ocrspring.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRole(String role);
    List<Role> findByPermissionsIn(List<Integer> permissions);
    @Query("{ 'role' : { $regex: ?0, $options: 'i' } }")
    Optional<Role> findByRoleIgnoreCase(String role);
}
