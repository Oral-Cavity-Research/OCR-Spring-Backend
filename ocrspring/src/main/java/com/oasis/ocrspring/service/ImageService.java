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
