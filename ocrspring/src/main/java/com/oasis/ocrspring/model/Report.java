package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Report {
    private String telecon_id;
    private String report_name;
    @Id
    private String id;
    private String createdAt;

    private String updatedAt;
    public Report(){}

    public Report(String telecon_id, String report_name) {
        this.telecon_id = telecon_id;
        this.report_name = report_name;
    }

    public String getId() {
        return id;
    }

    public String getTelecon_id() {
        return telecon_id;
    }

    public void setTelecon_id(String telecon_id) {
        this.telecon_id = telecon_id;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", telecon_id='" + telecon_id + '\'' +
                ", report_name='" + report_name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
