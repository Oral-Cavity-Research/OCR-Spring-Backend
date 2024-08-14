package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.UserDto;
import com.oasis.ocrspring.model.Hospital;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.service.HospitalService;
import com.oasis.ocrspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/self")
public class UserController {


    @Autowired
    private UserService userservice;
    @Autowired
    private HospitalService hospitalService;

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @ApiIgnore
    public void redirrect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swaggr-ui.html");
    }

    @GetMapping("/")
    public ResponseEntity<?> getUser(String id) {

        Optional<User> user = userservice.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("User not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/hospitals")
    public ResponseEntity<?> getHospitalList() {

        try {
            List<Hospital> hospitals = hospitalService.allHospitalDetails();
            return new ResponseEntity<>(hospitals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUserDetail(String id, @RequestBody UserDto userBody) {
        //todo : need to get id part through authentication generate
        try {
            Optional<User> existingUser = userservice.getUserById(id);
            if (existingUser.isPresent()) {
                User updatedUser = userservice.updateUser(id, userBody);
                return ResponseEntity.ok(Map.of(
                        "id", updatedUser.getId(),
                        "username", updatedUser.getUsername(),
                        "hospital", updatedUser.getHospital(),
                        "contact_no", updatedUser.getContactNo(),
                        "availability", updatedUser.isAvailability(),
                        "message", "User details updated successfully"
                ));
            } else {
                return ResponseEntity.status(401).body(Map.of("message", "User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Internal Server Error"));
        }
    }
}
