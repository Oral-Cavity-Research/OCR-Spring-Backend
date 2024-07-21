package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @PostMapping("/signup")
    public String userSignup(){
        return "/api/auth/signup";
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
