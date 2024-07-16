//package com.oasis.ocrspring.service;
//
//import com.oasis.ocrspring.model.Image;
//import com.oasis.ocrspring.model.Request;
//import com.oasis.ocrspring.repository.ImageRepository;
//import com.oasis.ocrspring.repository.RequestRepository;
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
//@Service
//public class ImageService {
//    @Autowired
//    private ImageRepository imageRepo;
//    private static final String IMAGE_DIRECTORY ="src/main/java/com/oasis/ocrspring/Storage/images";
//    public List<Image> AllImageDetails(){
//        System.out.println("appeared in service layer");
//        return imageRepo.findAll();
//    }
//    public List<Image> uploadImages(MultipartFile[] files, Image data) throws IOException {
//        List<Image> uploadedImages = new ArrayList<>();
//        for(MultipartFile file: files) {
//            try {
//                Path path = Paths.get(IMAGE_DIRECTORY + file.getOriginalFilename());
//                Files.createDirectories(path.getParent());
//                Files.write(path, file.getBytes());
//                //create new Image object for each file and copy the image data
//                Image image = new Image();
//                image.setTelecon_entry_id(data.getTelecon_entry_id());
//                image.setImage_name(file.getOriginalFilename());
//                image.setLocation(path.toString());
//                image.setClinical_diagnosis(data.getClinical_diagnosis());
//                image.setLesions_appear(data.getLesions_appear());
//                image.setAnnotation(data.getAnnotation());
//                image.setPredicted_cat(data.getPredicted_cat());
//
//                //save images to database
//                imageRepo.save(image);
//                uploadedImages.add(image);
//            }catch(Exception e){
//                throw new RuntimeException("Failed to store file"+ file.getOriginalFilename());
//            }
//        }
//        return uploadedImages;
//    }
//}
package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Image;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepo;
    public List<Image> AllImageDetails(){
        System.out.println("appeared in service layer");
        return imageRepo.findAll();
    }
}
