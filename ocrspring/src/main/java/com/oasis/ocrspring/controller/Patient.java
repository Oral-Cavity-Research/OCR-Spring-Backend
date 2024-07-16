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
public class Patient {
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


        return patientService.AllPatientDetails();
    }


    //check if a patient exists
    @GetMapping("/check/{id}")
    public String checkPatient(long id){
        //print patient id
        return "/api/user/patient/check/"+ id;
    }

    //get one id
    @GetMapping("/{id}")
    public com.oasis.ocrspring.model.Patient getPatientById(String id){
        //print patient id
        return patientService.getPatientById(id);
    }

    //get one shared id
    //id is patient id
    @GetMapping("/shared/{id}")
    public String getSharedPatient(long id){
        //print patient id
        return "/api/user/patient/shared/"+ id;
    }

    //get available reviewers
    @GetMapping("/reviewer/all")
    public String getReviewers(){
        return "/api/user/patient/reviewer/all";
    }

}
