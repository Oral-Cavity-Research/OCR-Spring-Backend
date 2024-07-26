package com.oasis.ocrspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.ocrspring.dto.ReportsRequestDto;
import com.oasis.ocrspring.dto.TeleconRequestDto;
import com.oasis.ocrspring.dto.UploadReportResponse;
import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReportRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.service.ImageService;
import com.oasis.ocrspring.service.ReportService;
import com.oasis.ocrspring.service.TeleconEntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ReportService reportServ;

    @PostMapping(value = "/images/{id}")
//    @PreAuthorize("hasAuthority('ROLE_USER)")
    public ResponseEntity<?> uploadImages(
            @PathVariable String id,
            @RequestBody TeleconRequestDto data) throws IOException {
        return imageService.uploadImages(data, id);
    }
    @PostMapping("/files")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws  IOException{
        return ResponseEntity.status(HttpStatus.OK).body(imageService.uploadFiles(files));
    }

    @PostMapping(value = "/reports/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadReportResponse> uploadReports(@PathVariable String id,
                                                              @RequestPart("data") ReportsRequestDto data,
                                                              @RequestPart("files") List<MultipartFile> files ) {

        return reportServ.uploadReports(data,id,files);
    }
//    public ResponseEntity<?> uploadReportFiles(@RequestParam MultipartFile files){
//        return ResponseEntity.status(HttpStatus.OK).body(reportServ.uploadReports(files));
//    }

    @PostMapping("/patient")
    public String addConsentForm() {
        return "/api/user/upload/patient";
    }

}
