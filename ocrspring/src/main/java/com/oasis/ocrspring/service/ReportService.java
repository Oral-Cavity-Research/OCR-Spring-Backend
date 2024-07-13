package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.Report;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.ReportRepository;
import com.oasis.ocrspring.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepo;
    public List<Report> AllReportDetails(){
        System.out.println("appeared in service layer");
        return reportRepo.findAll();
    }
}
