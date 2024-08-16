package com.oasis.ocrspring.controller;


import com.oasis.ocrspring.dto.PatientDetailsResDto;
import com.oasis.ocrspring.dto.SearchPatientDto;
import com.oasis.ocrspring.dto.UpdatePatientDto;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.service.PatientService;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
import com.oasis.ocrspring.service.auth.AuthenticationToken;
import com.oasis.ocrspring.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/user/patient/")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthenticationToken authenticationToken;

    @Autowired
    private TokenService tokenService;



    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }


    @PostMapping("update/{id}")
    public ResponseEntity<?> updatePatient(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody UpdatePatientDto updatePatient)throws IOException {

        authenticationToken.authenticateRequest(request, response);


        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized Access"));
        }
        String clinicianId=request.getAttribute("_id").toString();
        Optional<Patient> patient = patientService.getPaitentByIdAndClinicianId(id, clinicianId);
        if(patient.isEmpty()){
            return ResponseEntity.status(401).body(new ErrorMessage("Patient ID does not exist"));
        }

        Patient updatedPatent =patientService.findAndUpdate(id,clinicianId,updatePatient);
        if(updatedPatent==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Unauthorized Access"));
        }

        Map<String, Object> finalRes = new HashMap<>(updatedPatent.toMap()); // Assuming you have a method to convert Patient to Map
        finalRes.put("message", "Successfully added");

        return ResponseEntity.ok(finalRes);

    }



    //get all patients
    @GetMapping("/get")
    public ResponseEntity<?> getPatient(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false)String filter
    ) throws IOException {
        authenticationToken.authenticateRequest(request, response);

        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized Access"));
        }

        String clinicianId=request.getAttribute("_id").toString();

        int pageSize = 20;
        int pageQuery = page == null ? 1 : Integer.parseInt(page);
        String searchQuery = search == null ? "" : search;
        Sort.Direction sortDirection = sort == null || sort.equals("false") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort Order;

        Sort.Order sortOrder;
        switch (filter != null ? filter : "") {
            case "Name":
                sortOrder = Sort.Order.by("patientName").with(sortDirection);
                break;
            case "Age":
                sortOrder = Sort.Order.by("DOB").with(sortDirection);
                break;
            case "Gender":
                sortOrder = Sort.Order.by("gender").with(sortDirection);
                break;
            case "Created Date":
                sortOrder = Sort.Order.by("createdAt").with(sortDirection);
                break;
            case "Updated Date":
                sortOrder = Sort.Order.by("updatedAt").with(sortDirection);
                break;
            default:
                sortOrder = Sort.Order.by("patientId").with(sortDirection);
                break;
        }



        List<SearchPatientDto> patients;

        try{
            if(searchQuery.isEmpty()){
                patients = patientService.getAllPatients(clinicianId,pageQuery,pageSize,Sort.by(sortOrder));
            }else{
                patients = patientService.searchPatients(clinicianId,searchQuery,pageQuery,pageSize,Sort.by(sortOrder));

            }
            Map<String, Object> finalRes = new HashMap<>(); // Assuming you have a method to convert Patient to Map
            finalRes.put("patients", patients);
            return ResponseEntity.ok(finalRes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }



    }


    //check if a patient exists
    @GetMapping("/check/{id}")
    public ResponseEntity<?> checkPatient(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        authenticationToken.authenticateRequest(request, response);

        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized Access"));
        }
        try {
            Patient patient = patientService.getPatientByPatientIDAndClinicianId(id, request.getAttribute("_id").toString());
            if (patient != null) {
                return ResponseEntity.status(200).body(Collections.singletonMap("exists", true));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap("exists", false));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    //get one id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        authenticationToken.authenticateRequest(request, response);

        if(!tokenService.checkPermissions(request, Collections.singletonList("300"))){
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized Access"));
        }
        try{
            Optional<Patient> patientOptional =patientService.getPaitentByIdAndClinicianId(id,request.getAttribute("_id").toString());
            if(patientOptional.isEmpty()){
                return ResponseEntity.status(404).body(new ErrorMessage("Patient not found"));
            }
            Patient patient = patientOptional.get();
            return ResponseEntity.ok( new PatientDetailsResDto(
                    patient.getSystemicDisease(),
                    patient.getId().toString(),
                    patient.getPatientId(),
                    patient.getClinicianId().toString(),
                    patient.getPatientName(),
                    patient.getRiskFactors(),
                    patient.getGender(),
                    patient.getHistoDiagnosis(),
                    patient.getMedicalHistory(),
                    patient.getFamilyHistory(),
                    patient.getContactNo(),
                    patient.getConsentForm(),
                    patient.getCreatedAt().toString(),
                    patient.getUpdatedAt().toString()
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    //get one shared id
    //id is patient id
    @GetMapping("/shared/{id}")
    public com.oasis.ocrspring.model.Patient getSharedPatient(String id, @RequestHeader String reviewId) {
        return patientService.sharedPatient(id, reviewId);
    }

    //get available reviewers
    @GetMapping("/reviewer/all")
    public String getReviewers() {
        //todo : should add user id and his authentication checking
        //todo : should complete role service
        return "/api/user/patient/reviewer/all";
    }
}
