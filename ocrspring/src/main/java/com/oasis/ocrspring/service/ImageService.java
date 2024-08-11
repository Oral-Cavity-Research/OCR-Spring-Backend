package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ImageRequestDto;
import com.oasis.ocrspring.dto.UploadImageResponse;
import com.oasis.ocrspring.model.Image;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ImageRepository;
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

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepo;
    @Autowired
    private TeleconEntriesService teleconServices;
    @Value("src/main/Storage/images")
    private String uploadDir;

    public List<Image> allImageDetails() {
        return imageRepo.findAll();
    }


    public ResponseEntity<UploadImageResponse> uploadImages(ImageRequestDto data,
                                                            String id,
                                                            List<MultipartFile> files) throws IOException {
        List<Image> uploadedImages = new ArrayList<>();
        List<String> imageIds_ = new ArrayList<>();
        List<String> imageURIs = new ArrayList<>();
        try {
            TeleconEntry teleconEntry = teleconServices.findByID(id);
            if (teleconEntry != null && teleconEntry.getClinicianId().equals(getAuthenticatedUser())) {
                try {
                    for (MultipartFile file : files) {
                        //Save the image
                        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                        try {
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


                            //create new Image object for each file and copy the image data
                            Image image = new Image();
                            image.setTeleconEntryId(data.getTeleconId());
                            image.setImageName(data.getImageName());
                            image.setLocation(data.getLocation());
                            image.setClinicalDiagnosis(data.getClinicalDiagnosis());
                            image.setLesionsAppear(data.getLesionsAppear());
                            image.setAnnotation(data.getAnnotation());
                            image.setPredictedCat(data.getPredictedCat());
                            image.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                            image.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

                            //save images to database
                            imageRepo.save(image);
                            //imageIds_.add(image.getId());//Image ID list
                            uploadedImages.add(image);//Image Model list

                        } catch (Exception e) {//1st
                            return ResponseEntity.status(500).body(new UploadImageResponse(null
                                    , "Internal Server Error")); //Unable to save the file
                        }
                    }
                    // Extract image IDs and add them to the teleconEntry
                    List<String> imageIds = uploadedImages.stream().map(Image::getId).toList();
                    List<String> existedImageIds = teleconEntry.getImages();
                    if (existedImageIds.isEmpty()) {
                        existedImageIds = new ArrayList<>();
                    }

                    existedImageIds.addAll(imageIds);
                    teleconEntry.setImages(existedImageIds);
                    teleconEntry.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
                    teleconServices.save(teleconEntry);

                    return ResponseEntity.status(200).body(new UploadImageResponse(uploadedImages, "Images Uploaded Successfully"));

                } catch (Exception e) {//2nd
                    return ResponseEntity.status(404).body(new UploadImageResponse(null, "Internal Server Error"));
                }

            } else if (teleconEntry == null) {
                return ResponseEntity.status(500).body(new UploadImageResponse(null, "Entry Not Found"));
                //throw new Exception("Entry Not found");

            } else {
                return ResponseEntity.status(401).body(new UploadImageResponse(null, "Unauthorized Access"));
            }
        } catch (Exception e) {
            //return null;
            //throw new Exception("Internal Server Error",e);
            return ResponseEntity.status(500).body(new UploadImageResponse(null, "Internal Server Error"));
        }

    }

    private String getAuthenticatedUser() {
        return "641060a61530810142e045de";
    }

    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
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
