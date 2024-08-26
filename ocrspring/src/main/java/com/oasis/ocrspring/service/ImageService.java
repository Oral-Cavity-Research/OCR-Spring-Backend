package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ImageRequestDto;
import com.oasis.ocrspring.dto.UploadImageResponse;
import com.oasis.ocrspring.model.Image;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ImageRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepo;
    @Autowired
    private TeleconEntriesService teleconServices;
    @Value("${uploadDir}")
    private String uploadDir;

    public List<Image> allImageDetails() {
        return imageRepo.findAll();
    }


    public ResponseEntity<UploadImageResponse> uploadImages(ImageRequestDto data,
                                                            String id,
                                                            String clinicianId,
                                                            List<MultipartFile> files) throws IOException {
        List<Image> uploadedImages = new ArrayList<>();
        List<String> imageURIs = new ArrayList<>();
        final String errorMessage = "Internal Server Error";
        TeleconEntry teleconEntry;
        try {
            teleconEntry = teleconServices.findByID(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new UploadImageResponse(null, errorMessage));
        }

        if (teleconEntry == null || teleconEntry.getClinicianId().toString().equals(clinicianId)) {
            return ResponseEntity.status(404).body(new UploadImageResponse(null, "Entry Not Found"));
        }

        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            try {
                extracted(file, fileName, imageURIs);
                Image image = getImage(data);
                imageRepo.save(image);
                uploadedImages.add(image);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(new UploadImageResponse(null, errorMessage));
            }
        }

        try {
            List<ObjectId> imageIds = uploadedImages.stream().map(Image::getId).toList();
            List<ObjectId> existedImageIds = teleconEntry.getImages();
            if (existedImageIds.isEmpty()) {
                existedImageIds = new ArrayList<>();
            }

            existedImageIds.addAll(imageIds);
            teleconEntry.setImages(existedImageIds);
            teleconEntry.setUpdatedAt(LocalDateTime.now());
            teleconServices.save(teleconEntry);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new UploadImageResponse(null, errorMessage));
        }

        return ResponseEntity.status(200).body(new UploadImageResponse(uploadedImages, "Images Uploaded Successfully"));
    }

    private void extracted(MultipartFile file, String fileName, List<String> imageURIs) throws IOException {
        Path path = Paths.get(uploadDir + File.separator + fileName);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        //useful for creating uri to check the report
        String fileDownUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files")
                .path(fileName)
                .toUriString();
        imageURIs.add(fileDownUri);
        //How the Naming convension of the files work
    }

    private static Image getImage(ImageRequestDto data) {
        //create new Image object for each file and copy the image data
        Image image = new Image();
        image.setTeleconEntryId(data.getTeleconId());
        image.setImageName(data.getImageName());
        image.setLocation(data.getLocation());
        image.setClinicalDiagnosis(data.getClinicalDiagnosis());
        image.setLesionsAppear(data.getLesionsAppear());
        image.setAnnotation(data.getAnnotation());
        image.setPredictedCat(data.getPredictedCat());
        image.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        image.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        return image;
    }

    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            try {
                Path path = Paths.get(uploadDir + File.separator + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/files/")
                        .path(fileName)
                        .toUriString();
                uploadedFiles.add(fileDownloadUri);

            } catch (IOException ex) {
                throw new IOException("Couldn't save file: " + fileName);
            }
        }
        return uploadedFiles;
    }
}
