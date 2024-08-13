package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Assignment;
import com.oasis.ocrspring.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepo;

    public List<Assignment> allAssignmentDetails() {
        return assignmentRepo.findAll();
    }
}
