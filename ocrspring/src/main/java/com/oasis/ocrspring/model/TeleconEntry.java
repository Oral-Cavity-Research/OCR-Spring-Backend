package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "teleconentries")
public class TeleconEntry {

    @Id
    private String id; // MongoDB typically uses String for IDs

    private String patient;
    private String clinician_id;
    private String complaint;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String findings;
    private List<String> current_habits;
    private boolean updated;
    private List<String> reviewers;
    private List<String> reviews;
    private List<String> images;
    private List<String> reports;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    public TeleconEntry(){}

    public TeleconEntry( String patient, String clinician_id, String complaint, LocalDateTime start_time, LocalDateTime end_time, String findings, List<String> current_habits, boolean updated, List<String> reviewers, List<String> reviews, List<String> images, List<String> reports, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.patient = patient;
        this.clinician_id = clinician_id;
        this.complaint = complaint;
        this.start_time = start_time;
        this.end_time = end_time;
        this.findings = findings;
        this.current_habits = current_habits;
        this.updated = updated;
        this.reviewers = reviewers;
        this.reviews = reviews;
        this.images = images;
        this.reports = reports;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(String clinician_id) {
        this.clinician_id = clinician_id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public List<String> getCurrent_habits() {
        return current_habits;
    }

    public void setCurrent_habits(List<String> current_habits) {
        this.current_habits = current_habits;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public List<String> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<String> reviewers) {
        this.reviewers = reviewers;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TeleconEntry{" +
                "id='" + id + '\'' +
                ", patient='" + patient + '\'' +
                ", clinician_id='" + clinician_id + '\'' +
                ", complaint='" + complaint + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", findings='" + findings + '\'' +
                ", current_habits=" + current_habits +
                ", updated=" + updated +
                ", reviewers=" + reviewers +
                ", reviews=" + reviews +
                ", images=" + images +
                ", reports=" + reports +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
