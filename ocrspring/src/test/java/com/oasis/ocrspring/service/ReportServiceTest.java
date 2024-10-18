package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ReportsRequestDto;
import com.oasis.ocrspring.dto.UploadReportResponse;
import com.oasis.ocrspring.model.Report;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ReportRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReportServiceTest {

    @Mock
    private ReportRepository reportRepo;

    @Mock
    private TeleconEntriesService teleconServ;

    @Mock
    private TeleconEntriesRepository teleconRepo;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reportService = new ReportService(reportRepo, teleconServ, teleconRepo, "test-reports");
    }

    @Test
    void testUploadReports_HappyPath() {
        // Arrange
        String teleconId = new ObjectId().toHexString();
        String clinicianId = new ObjectId().toHexString();
        ReportsRequestDto requestDto = new ReportsRequestDto(new ObjectId(), "Test Report");

        List<MultipartFile> files = new ArrayList<>(); // Mock some MultipartFile instances
        TeleconEntry mockEntry = new TeleconEntry();
        mockEntry.setId(new ObjectId(teleconId));
        mockEntry.setClinicianId(new ObjectId(clinicianId));
        mockEntry.setReports(new ArrayList<>());

        when(teleconRepo.findById(any(ObjectId.class))).thenReturn(Optional.of(mockEntry));
        when(reportRepo.save(any(Report.class))).thenReturn(new Report());

        // Act
        ResponseEntity<UploadReportResponse> response = reportService.uploadReports(requestDto, teleconId, clinicianId, files);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Reports Uploaded Successfully", response.getBody().getMessage());
    }

    @Test
    void testUploadReports_EntryNotFound() {
        // Arrange
        String teleconId = new ObjectId().toHexString();
        String clinicianId = new ObjectId().toHexString();
        ReportsRequestDto requestDto = new ReportsRequestDto(new ObjectId(), "Test Report");

        List<MultipartFile> files = new ArrayList<>();

        when(teleconRepo.findById(any(ObjectId.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UploadReportResponse> response = reportService.uploadReports(requestDto, teleconId, clinicianId, files);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Entry Not Found", response.getBody().getMessage());
    }

    @Test
    void testUploadReports_FileCountExceedsLimit() {
        // Arrange
        String teleconId = new ObjectId().toHexString();
        String clinicianId = new ObjectId().toHexString();
        ReportsRequestDto requestDto = new ReportsRequestDto(new ObjectId(), "Test Report");

        List<MultipartFile> files = new ArrayList<>(); // Mock three or more files to simulate the limit exceeded case

        TeleconEntry mockEntry = new TeleconEntry();
        mockEntry.setId(new ObjectId(teleconId));
        mockEntry.setClinicianId(new ObjectId(clinicianId));
        mockEntry.setReports(new ArrayList<>());

        when(teleconRepo.findById(any(ObjectId.class))).thenReturn(Optional.of(mockEntry));

        // Act
        ResponseEntity<UploadReportResponse> response = reportService.uploadReports(requestDto, teleconId, clinicianId, files);

        // Assert
        assertEquals(404, response.getStatusCodeValue()); // Should return an error status
        assertNotNull(response.getBody());
        assertEquals("Entry Not Found", response.getBody().getMessage());
    }
}
