package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ImageRequestDto;
import com.oasis.ocrspring.dto.UploadImageResponse;
import com.oasis.ocrspring.model.Image;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private TeleconEntriesRepository teleconEntriesRepository;

    @Mock
    private TeleconEntriesService teleconEntriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadImages_Success() {
        // Arrange
        ImageRequestDto requestDto = new ImageRequestDto();
        requestDto.setTeleconId(new ObjectId("507f1f77bcf86cd799439011"));
        requestDto.setImageName("testImage.jpg");
        requestDto.setLocation("/images/testImage.jpg");
        requestDto.setClinicalDiagnosis("Diagnosis");
        requestDto.setLesionsAppear(true);
        requestDto.setPredictedCat("Cat");

        List<MultipartFile> files = new ArrayList<>();
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("testImage.jpg");
        files.add(mockFile);

        TeleconEntry teleconEntry = new TeleconEntry();
        teleconEntry.setId(new ObjectId("507f1f77bcf86cd799439011"));
        teleconEntry.setClinicianId(new ObjectId("507f1f77bcf86cd799439012"));

        when(teleconEntriesRepository.findById(new ObjectId("507f1f77bcf86cd799439011"))).thenReturn(Optional.of(teleconEntry));

        // Act
        ResponseEntity<UploadImageResponse> response = imageService.uploadImages(requestDto, "507f1f77bcf86cd799439011", "507f1f77bcf86cd799439012", files);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Images Uploaded Successfully", response.getBody().getMessage());

        // Verify the image is saved
        ArgumentCaptor<Image> imageCaptor = ArgumentCaptor.forClass(Image.class);
        verify(imageRepository, times(1)).save(imageCaptor.capture());
        Image savedImage = imageCaptor.getValue();
        assertEquals(requestDto.getImageName(), savedImage.getImageName());
        assertEquals(requestDto.getLocation(), savedImage.getLocation());
    }

    @Test
    void testUploadImages_TeleconEntryNotFound() {
        // Arrange
        ImageRequestDto requestDto = new ImageRequestDto();
        requestDto.setTeleconId(new ObjectId("507f1f77bcf86cd799439011"));
        requestDto.setImageName("testImage.jpg");

        List<MultipartFile> files = new ArrayList<>();
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("testImage.jpg");
        files.add(mockFile);

        when(teleconEntriesRepository.findById(new ObjectId("507f1f77bcf86cd799439011"))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UploadImageResponse> response = imageService.uploadImages(requestDto, "507f1f77bcf86cd799439011", "507f1f77bcf86cd799439012", files);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Entry Not Found", response.getBody().getMessage());
    }

    @Test
    void testUploadImages_TooManyFiles() {
        // Arrange
        ImageRequestDto requestDto = new ImageRequestDto();
        requestDto.setTeleconId(new ObjectId("507f1f77bcf86cd799439011"));
        requestDto.setImageName("testImage.jpg");

        List<MultipartFile> files = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            MultipartFile mockFile = mock(MultipartFile.class);
            when(mockFile.getOriginalFilename()).thenReturn("testImage" + i + ".jpg");
            files.add(mockFile);
        }

        TeleconEntry teleconEntry = new TeleconEntry();
        teleconEntry.setId(new ObjectId("507f1f77bcf86cd799439011"));
        teleconEntry.setClinicianId(new ObjectId("507f1f77bcf86cd799439012"));

        when(teleconEntriesRepository.findById(new ObjectId("507f1f77bcf86cd799439011"))).thenReturn(Optional.of(teleconEntry));

        // Act
        ResponseEntity<UploadImageResponse> response = imageService.uploadImages(requestDto, "507f1f77bcf86cd799439011", "507f1f77bcf86cd799439012", files);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().getMessage());
    }

}
