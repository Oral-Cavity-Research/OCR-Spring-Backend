package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ErrorResponseDto;
import com.oasis.ocrspring.model.Assignment;
import com.oasis.ocrspring.repository.AssignmentRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepo;

    @InjectMocks
    private AssignmentService assignmentService;

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        // Set up test data
        assignment = new Assignment();
        assignment.setId(new ObjectId("507f1f77bcf86cd799439011"));
        assignment.setReviewed(false);
    }

    @Test
    void allAssignmentDetailsTest() {
        // Mock repository response
        when(assignmentRepo.findAll()).thenReturn(List.of(assignment));

        // Call the service method
        List<Assignment> result = assignmentService.allAssignmentDetails();

        // Verify the behavior and assert the result
        verify(assignmentRepo).findAll();
        assertEquals(1, result.size());
        assertEquals(assignment, result.get(0));
    }

    @Test
    void getUnreviewedEntryCountSuccessTest() {
        // Mock repository response
        ObjectId clinicianObjectId = new ObjectId("507f1f77bcf86cd799439012");
        when(assignmentRepo.countByReviewerIdAndReviewedFalse(clinicianObjectId)).thenReturn(5L);

        // Call the service method
        ResponseEntity<?> response = assignmentService.getUnreviewedEntryCount("507f1f77bcf86cd799439012");

        // Verify the behavior and assert the result
        verify(assignmentRepo).countByReviewerIdAndReviewedFalse(clinicianObjectId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("count", 5L), response.getBody());
    }

    @Test
    void getUnreviewedEntryCountErrorTest() {
        // Mock repository to throw an exception
        ObjectId clinicianObjectId = new ObjectId("507f1f77bcf86cd799439012");
        when(assignmentRepo.countByReviewerIdAndReviewedFalse(clinicianObjectId)).thenThrow(new RuntimeException("Database error"));

        // Call the service method
        ResponseEntity<?> response = assignmentService.getUnreviewedEntryCount("507f1f77bcf86cd799439012");

        // Verify the behavior and assert the result
        verify(assignmentRepo).countByReviewerIdAndReviewedFalse(clinicianObjectId);
        assertEquals(500, response.getStatusCodeValue());

        // Check the ErrorResponseDto content
        ErrorResponseDto errorResponse = (ErrorResponseDto) response.getBody();
        assertEquals("Internal Server Error!", errorResponse.getMessage());
        assertEquals("java.lang.RuntimeException: Database error", errorResponse.getError());
    }

}
