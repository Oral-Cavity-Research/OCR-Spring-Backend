package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository RoleRepo;
    public List<Role> AllRoleDetails(){

        return RoleRepo.findAll();
    }
    public Role createRole(Role Role){

        return RoleRepo.save(Role);
    }
}

