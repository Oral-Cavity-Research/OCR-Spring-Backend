package com.oasis.ocrspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.ocrspring.dto.*;
import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.ReportRepository;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.service.ImageService;
import com.oasis.ocrspring.service.PatientService;
import com.oasis.ocrspring.service.ReportService;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
import com.oasis.ocrspring.service.TeleconEntriesService;
import com.oasis.ocrspring.service.auth.AuthenticationToken;
import com.oasis.ocrspring.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user/upload")
public class UploadController {
    @Autowired
    private PatientRepository patientrepo;
    @Autowired
    private ImageRepository imageRepo;
    @Autowired
    private ReportRepository reportRepo;
    @Autowired
    private TeleconEntriesRepository teleconEntriesRepo;
    @Autowired
    private TeleconEntriesService teleconServices;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReportService reportServ;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AuthenticationToken authenticationToken;
    @Autowired
    private TokenService tokenService;
    static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";

    @PostMapping(value = "/images/{id}")
    public ResponseEntity<UploadImageResponse> uploadImages(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id,
            @RequestPart("data") ImageRequestDto data,
            @RequestPart("files") List<MultipartFile> files) throws IOException {

        authenticationToken.authenticateRequest(request, response);
        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new UploadImageResponse(null,UNAUTHORIZED_ACCESS));
        }
        String clinicianId = request.getAttribute("_id").toString();
        return imageService.uploadImages(data, id,clinicianId, files);
    }

    @PostMapping(value = "/reports/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadReportResponse> uploadReports(HttpServletRequest request, HttpServletResponse response,
                                                              @PathVariable String id,
                                                              @RequestPart("data") ReportsRequestDto data,
                                                              @RequestPart("files") List<MultipartFile> files) throws IOException{

        authenticationToken.authenticateRequest(request, response);
        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new UploadReportResponse(null,UNAUTHORIZED_ACCESS));
        }
        String clinicianId=request.getAttribute("_id").toString();
        return reportServ.uploadReports(data, id,clinicianId, files);
    }

    @PostMapping("/patient")
    public ResponseEntity<?> addConsentForm(
            HttpServletRequest request, HttpServletResponse response,
            @RequestPart("data") ConsentRequestDto data,
            @RequestPart("files") MultipartFile files
    ) throws IOException {
        authenticationToken.authenticateRequest(request, response);
        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new ErrorMessage(UNAUTHORIZED_ACCESS));
        }
        String clinicianId = request.getAttribute("_id").toString();
        return patientService.addPatient(clinicianId, data, files);
    }
}
