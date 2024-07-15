package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin/auth")
public class adminAuth {
    @Autowired
    private UserRepository UserRepo;
    @ApiIgnore
    public void redirrect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swaggr-ui.html");
    }
    @PostMapping("/signup")
    public String adminSignUp(){
        return "/api/admin/auth/signup ";

    }
}

