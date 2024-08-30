package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final UserRepository userRepo;
    private final ImageRepository imageRepo;
    private final PatientRepository patientRepo;

    @Autowired
    public DashboardController(UserRepository userRepo, ImageRepository imageRepo, PatientRepository patientRepo) {
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.patientRepo = patientRepo;
    }

    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/percentages")
    public String riskHabitPercentage() {
        return "/api/dashboard/percentages";
    }

    @GetMapping("/totals")
    public String getTotal() {
        return "/api/dashboard/totals";
    }
}
