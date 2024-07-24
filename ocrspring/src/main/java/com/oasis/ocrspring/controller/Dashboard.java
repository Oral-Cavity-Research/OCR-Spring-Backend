package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class Dashboard {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/percentages")
    public ResponseEntity<List<String>> getRiskHabitPercentages() {
        List<String> percentages = dashboardService.getRiskHabitPercentage();
        return ResponseEntity.ok(percentages);
    }

    @GetMapping("/totals")
    public ResponseEntity<Map<String, Long>> getTotals() {
        Map<String, Long> totals = dashboardService.getTotals();
        return ResponseEntity.ok(totals);
    }
}
