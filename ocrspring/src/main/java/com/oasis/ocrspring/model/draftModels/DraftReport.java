package com.oasis.ocrspring.model.draftModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "draftreports")
public class DraftReport {
    @Id
    private String id;
    private String telecon_entry_id;
    private String report_name;

    public DraftReport(String telecon_entry_id, String report_name) {
        this.telecon_entry_id = telecon_entry_id;
        this.report_name = report_name;
    }

    public String getTelecon_entry_id() {
        return telecon_entry_id;
    }

    public void setTelecon_entry_id(String telecon_entry_id) {
        this.telecon_entry_id = telecon_entry_id;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    @Override
    public String toString() {
        return "DraftReport{" +
                "telecon_entry_id=" + telecon_entry_id +
                ", report_name='" + report_name + '\'' +
                '}';
    }
}
