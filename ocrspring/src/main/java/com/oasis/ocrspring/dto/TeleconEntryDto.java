package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.subModels.HabitDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeleconEntryDto {
    private String  _id; // MongoDB typically uses String for IDs

    private patientDetailsDto patient;
    private String clinician_id;
    private String complaint;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String findings;
    private List<HabitDto> current_habits;
    private boolean updated;
    private List<ReviewerDetailsDto> reviewers;
    private List<String> reviews;
    private List<String> images;
    private List<String> reports;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    public TeleconEntryDto(TeleconEntry teleconEntry,patientDetailsDto patient ,List<ReviewerDetailsDto> Reviewer){
        this._id = teleconEntry.getId().toString();
        this.patient = patient;
        this.clinician_id = teleconEntry.getClinicianId().toString();
        this.complaint = teleconEntry.getComplaint();
        this.start_time = teleconEntry.getStart_time();
        this.end_time = teleconEntry.getEnd_time();
        this.findings = teleconEntry.getFindings();
        this.current_habits = teleconEntry.getCurrent_habits();
        this.updated =teleconEntry.isUpdated();
        this.reviewers = Reviewer;
        this.reviews =teleconEntry.getReviews();
        this.images =teleconEntry.getImages();
        this.reports =teleconEntry.getReports();
        this.createdAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.updatedAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public patientDetailsDto getPatient() {
        return patient;
    }

    public void setPatient(patientDetailsDto patient) {
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

    public List<ReviewerDetailsDto> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<ReviewerDetailsDto> reviewers) {
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
}
