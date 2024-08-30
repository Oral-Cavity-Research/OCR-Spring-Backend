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
import com.oasis.ocrspring.service.responsemessages.ErrorMessage;
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
    private final PatientRepository patientRepo;
    private final ImageRepository imageRepo;
    private final ReportRepository reportRepo;
    private final TeleconEntriesRepository teleconEntriesRepo;
    private final TeleconEntriesService teleconServices;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;
    private final ReportService reportServ;
    private final PatientService patientService;
    private final AuthenticationToken authenticationToken;
    private final TokenService tokenService;

    @Autowired
    public UploadController(PatientRepository patientrepo, ImageRepository imageRepo, ReportRepository reportRepo,
                            TeleconEntriesRepository teleconEntriesRepo, TeleconEntriesService teleconServices,
                            ImageService imageService, ObjectMapper objectMapper, ReportService reportServ,
                            PatientService patientService, AuthenticationToken authenticationToken, TokenService tokenService) {
        this.patientRepo = patientrepo;
        this.imageRepo = imageRepo;
        this.reportRepo = reportRepo;
        this.teleconEntriesRepo = teleconEntriesRepo;
        this.teleconServices = teleconServices;
        this.imageService = imageService;
        this.objectMapper = objectMapper;
        this.reportServ = reportServ;
        this.patientService = patientService;
        this.authenticationToken = authenticationToken;
        this.tokenService = tokenService;
    }
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
