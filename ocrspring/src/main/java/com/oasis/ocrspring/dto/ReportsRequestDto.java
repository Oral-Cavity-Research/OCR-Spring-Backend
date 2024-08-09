package com.oasis.ocrspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

public class ReportsRequestDto {
    @JsonProperty("telecon_entry_id")
    private ObjectId teleconId;
    @JsonProperty("report_name")
    private String reportName;

    public ObjectId getTeleconId() {
        return teleconId;
    }

    public void setTeleconId(ObjectId teleconId) {
        this.teleconId = teleconId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
