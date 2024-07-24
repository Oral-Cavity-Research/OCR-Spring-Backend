package com.oasis.ocrspring.service;

import com.oasis.ocrspring.repository.ImageRepository;
import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.RiskFactorCount;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private PatientRepository patientRepo;

    public List<String> getRiskHabitPercentage() {
        List<RiskFactorCount> riskFactors = patientRepo.getRiskFactors();

        // Log the risk factors for debugging
        System.out.println("Risk Factors: " + riskFactors);

        if (riskFactors == null || riskFactors.isEmpty()) {
            return List.of("No risk factors found");
        }

        long totalPatients = patientRepo.count();
        List<String> percentages = new ArrayList<>();

        // Calculate percentage for each habit
        for (RiskFactorCount riskFactor : riskFactors) {
            String habit = riskFactor.getHabit();
            long count = riskFactor.getCount();
            double percentage = (double) count / totalPatients * 100;
            percentages.add(habit + ": " + percentage + "%");
        }

        return percentages;
    }

    public Map<String, Long> getTotals() {
        long totalDoctors = userRepo.count();
        long totalPatients = patientRepo.count();
        long totalImages = imageRepo.count();

        return Map.of(
            "doctors", totalDoctors,
            "patients", totalPatients,
            "images", totalImages
        );
    }
}
