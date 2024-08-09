package com.oasis.ocrspring.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "reports")
public class Report {
    @Field("telecon_entry_id")
    private ObjectId telecon_entry_id;
    private String report_name;
    @Id
    @Field("_id")
    private ObjectId id;
    private String createdAt;

    private String updatedAt;
    public Report(){}

    public Report(ObjectId telecon_entry_id, String report_name) {
        this.telecon_entry_id = telecon_entry_id;
        this.report_name = report_name;
    }

    public ObjectId getTelecon_entry_id() {
        return telecon_entry_id;
    }

    public void setTelecon_entry_id(ObjectId telecon_entry_id) {
        this.telecon_entry_id = telecon_entry_id;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
                "telecon_entry_id=" + telecon_entry_id +
                ", report_name='" + report_name + '\'' +
                ", id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
