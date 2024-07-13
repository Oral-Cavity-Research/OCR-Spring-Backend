package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.service.userService;
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
    private userService service;

    @ApiIgnore
    @RequestMapping(value ="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @GetMapping("/allUserDetails")
    public List<User> getAllUserDetails(){

        System.out.println("Get all user details");
        return service.AllDetails();

    }
    @PostMapping("/postUserDetails")
    public User postUserDetails(@RequestBody User user){
        return service.createUser(user);
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
