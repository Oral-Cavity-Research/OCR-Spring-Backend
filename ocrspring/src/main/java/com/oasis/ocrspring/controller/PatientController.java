package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/user/patient/")
public class PatientController
{
    @Autowired
    private PatientService patientService;



    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    //update patient details
    @PostMapping("update/{id}")
    public String updatePatient(long id){
        //print patient id
        return "/api/user/patient/update/"+ id;

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
