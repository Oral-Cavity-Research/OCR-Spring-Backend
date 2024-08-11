package com.oasis.ocrspring.service.draftServices;

import com.oasis.ocrspring.model.draftModels.DraftReport;
import com.oasis.ocrspring.repository.draftRepos.DraftReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftReportService {
    @Autowired
    private DraftReportRepository draftReportRepo;

    public List<DraftReport> allDraftReportDetails() {
        System.out.println("appeared in service layer");
        return draftReportRepo.findAll();
    }
}
