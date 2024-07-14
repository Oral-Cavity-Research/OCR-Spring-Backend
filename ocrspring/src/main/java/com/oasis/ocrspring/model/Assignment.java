package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assignments")
public class Assignment {
    @Id
    private String id;
    private String reviewer_id;
    private String telecon_entry;
    private Boolean checked;
    private Boolean reviewed;

    public Assignment(String reviewer_id, String telecon_entry, Boolean checked, Boolean reviewed) {
        this.reviewer_id = reviewer_id;
        this.telecon_entry = telecon_entry;
        this.checked = checked;
        this.reviewed = reviewed;
    }

    public String getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(String reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    public String getTelecon_entry() {
        return telecon_entry;
    }

    public void setTelecon_entry(String telecon_entry) {
        this.telecon_entry = telecon_entry;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "reviewer_id=" + reviewer_id +
                ", telecon_entry=" + telecon_entry +
                ", checked=" + checked +
                ", reviewed=" + reviewed +
                '}';
    }
}
