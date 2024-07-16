//package com.oasis.ocrspring.controller;
//
//import com.oasis.ocrspring.model.TeleconEntry;
//import com.oasis.ocrspring.repository.ImageRepository;
//import com.oasis.ocrspring.repository.PatientRepository;
//import com.oasis.ocrspring.repository.ReportRepository;
//import com.oasis.ocrspring.repository.TeleconEntriesRepository;
//import com.oasis.ocrspring.service.ImageService;
//import com.oasis.ocrspring.service.TeleconEntriesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import springfox.documentation.annotations.ApiIgnore;
////import org.springframework.security.access.prepost.PreAuthorize;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/user/upload")
//public class upload {
//    @Autowired
//    private PatientRepository PatientRepo;
//    @Autowired
//    private ImageRepository imageRepo;
//    @Autowired
//    private ReportRepository reportRepo;
//    @Autowired
//    private TeleconEntriesRepository TeleconEntriesRepo;
//    @Autowired
//    private TeleconEntriesService teleconServices;
//    @Autowired
//    private ImageService imageService;
//    @ApiIgnore
//    public void redirect(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/swagger-ui.html");
//    }
//
//    @PostMapping("/images/:id")
//    @PreAuthorize("hasAuthority('ROLE_USER)")
//    public ResponseEntity<?> uploadImages(@PathVariable String id, @RequestParam("files") MultipartFile[] files,
//                                          @RequestBody String data){
//        try{
//            TeleconEntry teleconEntry  = teleconServices.findByID(id);
//            if(teleconEntry != null && teleconEntry.getClinician_id().equals(getAuthenticatedUser()){
//                teleconServices.save(teleconEntry);
//           }
//        }
//    }
//    @PostMapping("/reports/:id")
//    public String uploadReports(String id){
//        return "/api/user/upload/reports/" +id;
//    }
//    @PostMapping("/patient")
//    public String addConsentForm(){
//        return "/api/user/upload/patient";
//    }
////    private String getAuthenticatedUser(){
////        return "641060a61530810142e045de";
////    }
////}
package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReportRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @PostMapping("/images/:id")
    public String uploadImage(String id){
        return "/api/user/upload/images/" +id;
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
