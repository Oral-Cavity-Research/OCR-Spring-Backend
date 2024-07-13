package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;

public class Report {
    @Id
    private String id;
    private String telecon_id;
    private String report_name;

    public Report(String telecon_id, String report_name) {
        this.telecon_id = telecon_id;
        this.report_name = report_name;
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

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", telecon_id='" + telecon_id + '\'' +
                ", report_name='" + report_name + '\'' +
                '}';
    }
}
