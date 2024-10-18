package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.HospitalDto;
import com.oasis.ocrspring.model.Hospital;
import com.oasis.ocrspring.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepo;

    @InjectMocks
    private HospitalService hospitalService;

    private Hospital hospital;
    private HospitalDto hospitalDto;

    @BeforeEach
    void setUp() {
        // Sample hospital data
        hospitalDto = new HospitalDto("1", "General Hospital", "General", "City", "123 Main St", "1234567890", "2023-01-01");
        hospital = new Hospital(hospitalDto);
        hospital.setId("1");
    }

    @Test
    void allHospitalDetailsTest() {
        // Mock repository to return a list of hospitals
        when(hospitalRepo.findAll()).thenReturn(List.of(hospital));

        // Call the service method
        List<Hospital> result = hospitalService.allHospitalDetails();

        // Verify behavior and assert results
        verify(hospitalRepo).findAll();
        assertEquals(1, result.size());
        assertEquals(hospital.getName(), result.get(0).getName());
    }

    @Test
    void addHospitalTest() {
        // Mock repository behavior for adding a hospital
        when(hospitalRepo.findByName(hospitalDto.getName())).thenReturn(Optional.empty());
        when(hospitalRepo.save(any(Hospital.class))).thenReturn(hospital);

        // Call the service method
        boolean result = hospitalService.addHospital(hospitalDto);

        // Verify behavior and assert results
        verify(hospitalRepo).findByName(hospitalDto.getName());
        verify(hospitalRepo).save(any(Hospital.class));
        assertTrue(result);
    }

    @Test
    void addHospitalAlreadyExistsTest() {
        // Mock repository behavior for existing hospital
        when(hospitalRepo.findByName(hospitalDto.getName())).thenReturn(Optional.of(hospital));

        // Call the service method
        boolean result = hospitalService.addHospital(hospitalDto);

        // Verify behavior and assert results
        verify(hospitalRepo).findByName(hospitalDto.getName());
        verify(hospitalRepo, never()).save(any(Hospital.class));
        assertFalse(result);
    }

    @Test
    void getHospitalByIdTest() {
        // Mock repository to return a hospital by ID
        when(hospitalRepo.findById("1")).thenReturn(Optional.of(hospital));

        // Call the service method
        Optional<Hospital> result = hospitalService.getHospitalById("1");

        // Verify behavior and assert results
        verify(hospitalRepo).findById("1");
        assertTrue(result.isPresent());
        assertEquals(hospital.getName(), result.get().getName());
    }

    @Test
    void updateHospitalTest() {
        // Mock repository to return a hospital by ID
        when(hospitalRepo.findById("1")).thenReturn(Optional.of(hospital));
        when(hospitalRepo.save(any(Hospital.class))).thenReturn(hospital);

        // Call the service method
        hospitalService.updateHospital("1", hospitalDto);

        // Verify behavior
        verify(hospitalRepo).findById("1");
        verify(hospitalRepo).save(any(Hospital.class));
    }

    @Test
    void updateHospitalNotFoundTest() {
        // Mock repository to return empty for hospital by ID
        when(hospitalRepo.findById("1")).thenReturn(Optional.empty());

        // Expect exception when updating a non-existing hospital
        assertThrows(RuntimeException.class, () -> hospitalService.updateHospital("1", hospitalDto));

        // Verify behavior
        verify(hospitalRepo).findById("1");
        verify(hospitalRepo, never()).save(any(Hospital.class));
    }

    @Test
    void deleteHospitalTest() {
        // Mock repository to return true for existing hospital ID
        when(hospitalRepo.existsById("1")).thenReturn(true);
        doNothing().when(hospitalRepo).deleteById("1");

        // Call the service method
        boolean result = hospitalService.deleteHospital("1");

        // Verify behavior and assert results
        verify(hospitalRepo).existsById("1");
        verify(hospitalRepo).deleteById("1");
        assertTrue(result);
    }

    @Test
    void deleteHospitalNotFoundTest() {
        // Mock repository to return false for non-existing hospital ID
        when(hospitalRepo.existsById("1")).thenReturn(false);

        // Call the service method
        boolean result = hospitalService.deleteHospital("1");

        // Verify behavior and assert results
        verify(hospitalRepo).existsById("1");
        verify(hospitalRepo, never()).deleteById(anyString());
        assertFalse(result);
    }
}
