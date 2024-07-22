package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.HospitalRepository;
import com.oasis.ocrspring.repository.UserRepository;

import com.oasis.ocrspring.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/self")
public class user {
    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private HospitalRepository hospitalRepo;

    @Autowired
    private userService userservice;

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
    public ResponseEntity<?> getUser(String _id)
    {

        Optional<User> user = userservice.getUserById(_id);

        if(user.isPresent()){

            return new ResponseEntity<>(user.get(), HttpStatus.OK);

        }else{

            return new ResponseEntity<>(new ErrorResponse("User not found"), HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/hospitals")
    public String getHospitalList(){
        return "/api/user/self/hospitals";
    }
    @PostMapping("/update")
    public String updateUserDetail(){
        return "/api/user/self/update";
    }
}
