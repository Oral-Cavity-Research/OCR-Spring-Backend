package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.PatientRepository;
import com.oasis.ocrspring.repository.draftRepos.DraftEntryRepository;
import com.oasis.ocrspring.repository.draftRepos.DraftReportRepository;
import com.oasis.ocrspring.repository.draftRepos.DraftimageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/draftentry")
public class DraftEntryController {
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private DraftReportRepository draftReportRepo;
    @Autowired
    private DraftimageRepository draftImageRepo;
    @Autowired
    private DraftEntryRepository draftEntryRepo;

    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @PostMapping("/add/{:id}")
    public String addDraftTeleconEntry(String id) {
        return "/api/user/draftentry/add/" + id;
    }

    @GetMapping("/get")
    public String getAllDraftEntries() {
        return "/api/user/draftentry/get";
    }

    @GetMapping("/get/patient/:id")
    public String getPtientEntries(String id) {
        return "/api/user/draftentry/get/patient" + id;
    }

    @GetMapping("/get/id")
    public String getEntryDetails(String id) {
        return "/api/user/draftentry/get" + id;
    }
}
