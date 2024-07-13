package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assigments")
public class Assignment {
    @Id
    private Long id;
    private Long reviewer_id;
    private Long telecon_entry;
    private Boolean checked;
    private Boolean reviewed;

    public Assignment(Long reviewer_id, Long telecon_entry, Boolean checked, Boolean reviewed) {
        this.reviewer_id = reviewer_id;
        this.telecon_entry = telecon_entry;
        this.checked = checked;
        this.reviewed = reviewed;
    }

    public Long getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(Long reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    public Long getTelecon_entry() {
        return telecon_entry;
    }

    public void setTelecon_entry(Long telecon_entry) {
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
