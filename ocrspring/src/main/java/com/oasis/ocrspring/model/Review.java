package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String telecon_entry_id;
    private String reviewer_id;
    private String provisional_diagnosis;
    private String management_suggestions;
    private String referral_suggestions;
    private String other_comments;

    public Review(String telecon_entry_id, String reviewer_id, String provisional_diagnosis, String management_suggestions, String referral_suggestions, String other_comments) {
        this.telecon_entry_id = telecon_entry_id;
        this.reviewer_id = reviewer_id;
        this.provisional_diagnosis = provisional_diagnosis;
        this.management_suggestions = management_suggestions;
        this.referral_suggestions = referral_suggestions;
        this.other_comments = other_comments;
    }

    public String getTelecon_entry_id() {
        return telecon_entry_id;
    }

    public void setTelecon_entry_id(String telecon_entry_id) {
        this.telecon_entry_id = telecon_entry_id;
    }

    public String getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(String reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    public String getProvisional_diagnosis() {
        return provisional_diagnosis;
    }

    public void setProvisional_diagnosis(String provisional_diagnosis) {
        this.provisional_diagnosis = provisional_diagnosis;
    }

    public String getManagement_suggestions() {
        return management_suggestions;
    }

    public void setManagement_suggestions(String management_suggestions) {
        this.management_suggestions = management_suggestions;
    }

    public String getReferral_suggestions() {
        return referral_suggestions;
    }

    public void setReferral_suggestions(String referral_suggestions) {
        this.referral_suggestions = referral_suggestions;
    }

    public String getOther_comments() {
        return other_comments;
    }

    public void setOther_comments(String other_comments) {
        this.other_comments = other_comments;
    }

    @Override
    public String toString() {
        return "Review{" +
                "telecon_entry_id='" + telecon_entry_id + '\'' +
                ", reviewer_id='" + reviewer_id + '\'' +
                ", provisional_diagnosis='" + provisional_diagnosis + '\'' +
                ", management_suggestions='" + management_suggestions + '\'' +
                ", referral_suggestions='" + referral_suggestions + '\'' +
                ", other_comments='" + other_comments + '\'' +
                '}';
    }
}
