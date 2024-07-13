package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.model.*;
import com.oasis.ocrspring.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private userService userservice;
    @Autowired
    private PatientService patientService;
    @Autowired                                  
    private RoleService roleService;
    @Autowired
    private RequestService requestService;

    @Autowired
    private TeleconEntriesService teleconEntriesService;
    @Autowired
    private ReviewService reviewService;
    @ApiIgnore
    @RequestMapping(value ="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @GetMapping("/allUserDetails")
    public List<User> getAllUserDetails(){


        return userservice.AllUserDetails();

    }
    @GetMapping("/allPatientDetails")
    public List<Patient> AllPatientDetails(){

        System.out.println("Get all user details");
        return patientService.AllPatientDetails();
    }
    @GetMapping("/allRoleDetails")
    public List<Role> AllRoleDetails(){

        System.out.println("Get all user details");
        return  roleService.AllRoleDetails();
    }

    @GetMapping("/allRequestDetails")
    public List<Request> AllRequestDetails() {

        return requestService.AllRequestDetails();
    }

    @GetMapping("/allTeleconEntryDetails")
    public List<TeleconEntry> AllTeleconEntryDetails() {

        return teleconEntriesService.AllTeleconEntriesDetails();
    }
    @GetMapping("/allReviewDetails")
    public List<Review> AllReviewDetails() {

        return reviewService.AllReviewDetails();
    }
    @PostMapping("/postUserDetails")
    public User postUserDetails(@RequestBody User user){
        return userservice.createUser(user);
    }
//    @PostMapping("/signup")
//    public ResponseEntity<String> createUser(@RequestBody User user){
//
//        if(service.getuserDetailsByEmail(user.getEmail())!=null){
//            return new ResponseEntity<>("user Already Exists", HttpStatus.CONFLICT);
//        }else{
//            service.createUser(user);
//            return new ResponseEntity<>("User Created",HttpStatus.CREATED);
//        }
}
