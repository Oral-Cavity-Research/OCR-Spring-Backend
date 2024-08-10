package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.dto.subdto.HabitDto;

import java.time.LocalDateTime;
import java.util.List;

public class patientTeleconResponse {
    private String _id; // MongoDB typically uses String for IDs

    private String patient;
    private String clinician_id;
    private String complaint;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String findings;
    private List<HabitDto> current_habits;
    private boolean updated;
    private List<String> reviewers;
    private List<String> reviews;
    private List<String> images;
    private List<String> reports;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public patientTeleconResponse(TeleconEntry teleconEntry) {
        this._id = teleconEntry.getId().toString();
        this.patient = teleconEntry.getPatient().toString();
        this.clinician_id = teleconEntry.getClinicianId().toString();
        this.complaint = teleconEntry.getComplaint();
        this.start_time = teleconEntry.getStartTime();
        this.end_time = teleconEntry.getEndTime();
        this.findings = teleconEntry.getFindings();
        this.current_habits = teleconEntry.getCurrentHabits();
        this.updated = teleconEntry.isUpdated();
        this.reviewers = teleconEntry.getReviewers();
        this.reviews = teleconEntry.getReviews();
        this.images = teleconEntry.getImages();
        this.reports = teleconEntry.getReports();
        this.createdAt = teleconEntry.getCreatedAt();
        this.updatedAt = teleconEntry.getUpdatedAt();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public List<HabitDto> getCurrent_habits() {
        return current_habits;
    }

    public void setCurrent_habits(List<HabitDto> current_habits) {
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
        return "patientTeleconResponse{" +
                "_id='" + _id + '\'' +
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
