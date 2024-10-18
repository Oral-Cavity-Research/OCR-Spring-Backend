package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RequestRepository;
import com.oasis.ocrspring.repository.UserRepository;
import com.oasis.ocrspring.service.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.MessagingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestServiceTest {

    @Mock
    private RequestRepository requestRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private RequestService requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRejectRequest_Success() throws MessagingException {
        // Arrange
        String requestId = "1";
        String reason = "Invalid data";
        Request request = new Request();
        request.setId(requestId);
        request.setEmail("user@example.com");
        request.setUserName("testUser");
        when(requestRepo.findById(requestId)).thenReturn(Optional.of(request));

        // Act
        boolean result = requestService.rejectRequest(requestId, reason);

        // Assert
        assertTrue(result);
        verify(requestRepo, times(1)).deleteById(requestId);
        verify(emailService, times(1)).sendEmail(
                eq("user@example.com"),
                eq("REJECT"),
                eq(reason),
                eq("testUser")
        );
    }

    @Test
    void testRejectRequest_NotFound() throws MessagingException {
        // Arrange
        String requestId = "1";
        String reason = "Invalid data";
        when(requestRepo.findById(requestId)).thenReturn(Optional.empty());

        // Act
        boolean result = requestService.rejectRequest(requestId, reason);

        // Assert
        assertFalse(result);
        verify(requestRepo, never()).deleteById(anyString());
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testAcceptRequest_Success() throws MessagingException {
        // Arrange
        String requestId = "1";
        String reason = "Welcome!";
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        Request request = new Request();
        request.setId(requestId);
        request.setEmail("user@example.com");
        request.setUserName("testUser");
        when(requestRepo.findById(requestId)).thenReturn(Optional.of(request));

        // Act
        requestService.acceptRequest(requestId, newUser, reason);

        // Assert
        verify(userRepo, times(1)).save(newUser);
        verify(requestRepo, times(1)).deleteById(requestId);
        verify(emailService, times(1)).sendEmail(
                eq("user@example.com"),
                eq("ACCEPT"),
                eq(reason),
                eq("testUser")
        );
    }

    @Test
    void testAcceptRequest_RequestNotFound() throws MessagingException {
        // Arrange
        String requestId = "1";
        String reason = "Welcome!";
        User newUser = new User();
        when(requestRepo.findById(requestId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            requestService.acceptRequest(requestId, newUser, reason);
        });
        verify(userRepo, never()).save(any(User.class));
        verify(requestRepo, never()).deleteById(anyString());
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString(), anyString());
    }
}
