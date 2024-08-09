package com.oasis.ocrspring.model;

import com.oasis.ocrspring.model.subModels.HabitDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "teleconentries")
public class TeleconEntry {

    @Id
    @Field("_id")
    private ObjectId  id; // MongoDB typically uses String for IDs

    private ObjectId patient;
    @Field("clinician_id")
    private ObjectId clinicianId;
    private String complaint;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String findings;
    private List<HabitDto> current_habits;
    private boolean updated;
    private List<ObjectId> reviewers;
    private List<String> reviews;
    private List<ObjectId> images;
    private List<ObjectId> reports;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    public TeleconEntry(){}

    public TeleconEntry(ObjectId id, ObjectId patient, ObjectId clinicianId, String complaint,
                        LocalDateTime start_time, LocalDateTime end_time, String findings,
                        List<HabitDto> current_habits, boolean updated,
                        List<ObjectId> reviewers, List<String> reviews, List<ObjectId> images,
                        List<ObjectId> reports, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.patient = patient;
        this.clinicianId = clinicianId;
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getPatient() {
        return patient;
    }

    public void setPatient(ObjectId patient) {
        this.patient = patient;
    }

    public ObjectId getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(ObjectId clinicianId) {
        this.clinicianId = clinicianId;
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

    public List<ObjectId> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<ObjectId> reviewers) {
        this.reviewers = reviewers;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public List<ObjectId> getImages() {
        return images;
    }

    public void setImages(List<ObjectId> images) {
        this.images = images;
    }

    public List<ObjectId> getReports() {
        return reports;
    }

    public void setReports(List<ObjectId> reports) {
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
                "id=" + id +
                ", patient=" + patient +
                ", clinicianId=" + clinicianId +
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
