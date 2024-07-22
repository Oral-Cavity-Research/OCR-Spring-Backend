package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.RequestRepository;
import com.oasis.ocrspring.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class userAuth {
    @Autowired
    private RequestRepository RequestRepo;
    @Autowired
    private userService userservice;
    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @PostMapping("/signup")
    public ResponseEntity<String> userSignup(@RequestBody Request request){
        String message = userservice.signup(request);
        if(message.equals("Request is sent successfully. You will receive an Email on acceptance")){
            return ResponseEntity.ok(message);
        }else{
            return ResponseEntity.status(401).body(message);
        }

    }
    @PostMapping("/verify")
    public String userVerify(){
        return "/api/auth/verify";
    }
    @PostMapping("/refreshToken")
    public String refreshToken(){
        return "/api/auth/refreshToken";
    }
    @PostMapping("revokeToken")
    public String revokeToken(){
        return "/api/auth/revokeToken";
    }

}
