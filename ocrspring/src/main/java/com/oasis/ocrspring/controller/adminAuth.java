package com.oasis.ocrspring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/admin/auth")
public class adminAuth {
    @PostMapping("/signup")
    public String adminSignUp(){
        return "/api/admin/auth/signup ";

    }
}

