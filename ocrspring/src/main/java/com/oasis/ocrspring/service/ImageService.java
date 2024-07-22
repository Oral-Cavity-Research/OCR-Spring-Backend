package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.TeleconRequestDto;
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
    public List<Image> AllImageDetails(){
        System.out.println("appeared in service layer");
        return imageRepo.findAll();
    }


    public ResponseEntity<?> uploadImages(TeleconRequestDto data, String id) throws IOException {
        try{
            TeleconEntry teleconEntry  = teleconServices.findByID(id);
            if(teleconEntry != null && teleconEntry.getClinician_id().equals(getAuthenticatedUser())) {
                System.out.println(id);

                List<Image> uploadedImages = new ArrayList<>();
                    //create new Image object for each file and copy the image data
                    try {

                        Image image = new Image();
                        image.setTelecon_entry_id(data.getTelecon_entry_id());
                        image.setImage_name(data.getImage_name());
                        image.setLocation(data.getLocation());
                        image.setClinical_diagnosis(data.getClinical_diagnosis());
                        image.setLesions_appear(data.getLesions_appear());
                        image.setAnnotation(data.getAnnotation());
                        image.setPredicted_cat(data.getPredicted_cat());
                        image.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                        image.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

                        //save images to database
                        imageRepo.save(image);
                        uploadedImages.add(image);
                    }catch(Exception e){
                        throw new RuntimeException("Failed to store file");
                    }
                // Extract image IDs and add them to the teleconEntry
                List<String> imageIds = uploadedImages.stream().map(Image::getId).toList();
                teleconEntry.getImages().addAll(imageIds);

                teleconServices.save(teleconEntry);
                return ResponseEntity.status(200).body("Images Uploaded Successfully");
            }else if(teleconEntry == null){
                return ResponseEntity.status(404).body("Entry Not Found");
                //throw new Exception("Entry Not found");

            }else{
                return ResponseEntity.status(401).body("Unauthorized Access");
            }
        }catch(Exception e){
            //return null;
            //throw new Exception("Internal Server Error",e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }

    }
    private String getAuthenticatedUser(){
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
               throw new IOException("Couldn't save file: "+fileName);
            }
        }
        return uploadedFiles;
    }
}
