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
