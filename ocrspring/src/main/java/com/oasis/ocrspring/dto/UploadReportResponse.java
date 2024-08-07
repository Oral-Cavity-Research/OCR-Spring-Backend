package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Report;

import java.util.List;

public class UploadReportResponse {
    private List<Report> docs;
    private String message;

    public UploadReportResponse(List<Report> uploadedReports, String message) {
        this.docs = uploadedReports;
        this.message = message;
    }

    public List<Report> getDocs() {
        return docs;
    }

    public void setDocs(List<Report> docs) {
        this.docs = docs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
