package com.oasis.ocrspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.ocrspring.dto.TeleconRequestDto;
import com.oasis.ocrspring.model.Image;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReportRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.service.ImageService;
import com.oasis.ocrspring.service.TeleconEntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//import static java.util.stream.Nodes.collect;

@RestController
@RequestMapping("/api/user/upload")
public class upload {
    @Autowired
    private PatientRepository PatientRepo;
    @Autowired
    private ImageRepository imageRepo;
    @Autowired
    private ReportRepository reportRepo;
    @Autowired
    private TeleconEntriesRepository TeleconEntriesRepo;
    @Autowired
    private TeleconEntriesService teleconServices;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ObjectMapper objectMapper;
    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @PostMapping(value = "/images/{id}",
            consumes = {"multipart/form-data"})
//    @PreAuthorize("hasAuthority('ROLE_USER)")
    public ResponseEntity<?> uploadImages(
            @PathVariable String id,
            //@RequestPart(value = "files") MultipartFile[] files,
            @RequestBody TeleconRequestDto data) throws IOException{
        System.out.println("Hiiiiii");
        return ResponseEntity.status(HttpStatus.OK).body(imageService.uploadImages(data,id));

    }
    @PostMapping("/reports/:id")
    public String uploadReports(String id){
        return "/api/user/upload/reports/" +id;
    }
    @PostMapping("/patient")
    public String addConsentForm(){
        return "/api/user/upload/patient";
    }

}
