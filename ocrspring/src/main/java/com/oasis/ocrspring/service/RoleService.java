package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepo;

    public List<Role> allRoleDetails() {
        return roleRepo.findAll();
    }

    public Role createRole(Role role) {
        return roleRepo.save(role);
    }

    public Optional<Role> getRoleByrole(String role) {
        return roleRepo.findByRole(role);
    }
}

