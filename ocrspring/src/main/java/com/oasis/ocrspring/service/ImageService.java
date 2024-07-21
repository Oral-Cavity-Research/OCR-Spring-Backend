package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.TeleconRequestDto;
import com.oasis.ocrspring.model.Image;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepo;
    @Autowired
    private TeleconEntriesService teleconServices;
    private static final String IMAGE_DIRECTORY ="src/main/java/com/oasis/ocrspring/Storage/images";
    public List<Image> AllImageDetails(){
        System.out.println("appeared in service layer");
        return imageRepo.findAll();
    }
    public List<Image> uploadImages( TeleconRequestDto data,String id) throws IOException {
        try{
            TeleconEntry teleconEntry  = teleconServices.findByID(id);
            if(teleconEntry != null && teleconEntry.getClinician_id().equals(getAuthenticatedUser())) {
                System.out.println(id);
                // Parse the data JSON string to an Image object
//                Image imagedata = objectMapper.readValue(data,Image.class);
//                Image imageD = new Image();
//                imageD.setTelecon_entry_id(data.getTelecon_entry_id());
//                imageD.setImage_name(data.getImage_name());
//                imageD.setLocation(data.getLocation());
//                imageD.setClinical_diagnosis(data.getClinical_diagnosis());
//                imageD.setLesions_appear(data.getLesions_appear());
//                imageD.setAnnotation(data.getAnnotation());
//                imageD.setPredicted_cat(data.getPredicted_cat());
                List<Image> uploadedImages = new ArrayList<>();
//                List<Image> uploadedImages = imageService.uploadImages(files,data);
                //for(MultipartFile file: files) {
                    //create new Image object for each file and copy the image data
                    try {
                        //Path path = Paths.get(IMAGE_DIRECTORY + file.getOriginalFilename());
                        //Files.createDirectories(path.getParent());
                        //Files.write(path, file.getBytes());

                        Image image = new Image();
                        image.setTelecon_entry_id(data.getTelecon_entry_id());
                        image.setImage_name(data.getImage_name());
                        image.setLocation(data.getLocation());
                        image.setClinical_diagnosis(data.getClinical_diagnosis());
                        image.setLesions_appear(data.getLesions_appear());
                        image.setAnnotation(data.getAnnotation());
                        image.setPredicted_cat(data.getPredicted_cat());

                        //save images to database
                        imageRepo.save(image);
                        uploadedImages.add(image);
                    }catch(Exception e){
                        throw new RuntimeException("Failed to store file");
                    }
                //}


                // Extract image IDs and add them to the teleconEntry
                List<String> imageIds = uploadedImages.stream().map(Image::getId).toList();
                teleconEntry.getImages().addAll(imageIds);

                teleconServices.save(teleconEntry);
                return uploadedImages;
            }else{
                //return ResponseEntity.status(404).body("Entry Not Found");
                throw new Exception("Entry Not found");

            }
        }catch(Exception e){
            return null;
            //throw new Exception("Internal Server Error",e);
        }

        //return uploadedImages;
    }
    private String getAuthenticatedUser(){
        return "641060a61530810142e045de";
    }
}
//
//package com.oasis.ocrspring.service;
//
//import com.oasis.ocrspring.model.Image;
//import com.oasis.ocrspring.repository.ImageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//@Service
//public class ImageService {
//    @Autowired
//    private ImageRepository imageRepo;
//
//    // Updated directory path
//    private static final String IMAGE_DIRECTORY ="src/main/java/com/oasis/ocrspring/Storage/images";
//
//    public List<Image> AllImageDetails() {
//        System.out.println("appeared in service layer");
//        return imageRepo.findAll();
//    }
//
//    public List<Image> uploadImages(MultipartFile[] files, Image data) throws IOException {
//        List<Image> uploadedImages = new ArrayList<>();
//        for (MultipartFile file : files) {
//            try {
//                Path path = Paths.get(IMAGE_DIRECTORY + "/" + file.getOriginalFilename());
//                Files.createDirectories(path.getParent());
//                Files.write(path, file.getBytes());
//
//                Image image = new Image();
//                image.setTelecon_entry_id(data.getTelecon_entry_id());
//                image.setImage_name(file.getOriginalFilename());
//                image.setLocation(path.toString());
//                image.setClinical_diagnosis(data.getClinical_diagnosis());
//                image.setLesions_appear(data.getLesions_appear());
//                image.setAnnotation(data.getAnnotation());
//                image.setPredicted_cat(data.getPredicted_cat());
//
//                // Save images to database
//                imageRepo.save(image);
//                uploadedImages.add(image);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
//            }
//        }
//        return uploadedImages;
//    }
//}
