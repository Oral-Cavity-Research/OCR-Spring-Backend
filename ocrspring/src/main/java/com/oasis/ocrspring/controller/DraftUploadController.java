package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.draftRepos.DraftEntryRepository;
import com.oasis.ocrspring.repository.draftRepos.DraftReportRepository;
import com.oasis.ocrspring.repository.draftRepos.DraftimageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/draftupload")
public class DraftUploadController
{
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
    @PostMapping("/images/:id")
    public String uploadDraftImages(String id){
        return "/api/user/draftupload/images/"+id;
    }
    @PostMapping("/reports/:id")
    public String uploadDraftReport(String id){
        return "/api/user/draftupload/reports/"+id;
    }

}
