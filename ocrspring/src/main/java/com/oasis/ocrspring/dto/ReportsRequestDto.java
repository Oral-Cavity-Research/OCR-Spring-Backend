package com.oasis.ocrspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportsRequestDto {
    @JsonProperty("telecon_entry_id")
    private String teleconId;
    @JsonProperty("report_name")
    private String reportName;

    public String getTeleconId() {
        return teleconId;
    }

    public void setTeleconId(String teleconId) {
        this.teleconId = teleconId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
