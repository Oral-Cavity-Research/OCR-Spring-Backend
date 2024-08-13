package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.UpdatePatientDto;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.service.PatientService;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
import com.oasis.ocrspring.service.auth.AuthenticationToken;
import com.oasis.ocrspring.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PatientController
{
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

        Map<String, Object> finalRes = new HashMap<>(patient.get().toMap()); // Assuming you have a method to convert Patient to Map
        finalRes.put("message", "Successfully added");

        return ResponseEntity.ok(finalRes);


    }



    //get all patients
    @GetMapping("/get")
    public List<com.oasis.ocrspring.model.Patient> getPatient(){
        //todo : should add user id and his authentication checking
        return patientService.AllPatientDetails();
    }


    //check if a patient exists
    @GetMapping("/check/{id}")
    public boolean checkPatient(String id){
        //todo : should add user id and his authentication checking
        return patientService.isexist(id);
    }

    //get one id
    @GetMapping("/{id}")
    public com.oasis.ocrspring.model.Patient getPatientById(String id){
        //todo : should add user id and his authentication checking
        return patientService.getPatientById(id);
    }

    //get one shared id
    //id is patient id
    @GetMapping("/shared/{id}")
    public com.oasis.ocrspring.model.Patient getSharedPatient(String id, @RequestHeader String review_id){

        //print patient id
        return patientService.sharedPatient(id, review_id);
    }

    //get available reviewers
    @GetMapping("/reviewer/all")
    public String getReviewers(){
        //todo : should add user id and his authentication checking
        //todo : should complete role service
        return "/api/user/patient/reviewer/all";
    }

}
