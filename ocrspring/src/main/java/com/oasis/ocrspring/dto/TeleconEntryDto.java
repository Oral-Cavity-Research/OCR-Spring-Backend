package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.dto.subdto.HabitDto;
import com.oasis.ocrspring.model.TeleconEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeleconEntryDto {
    private String id; // MongoDB typically uses String for IDs
    private PatientDetailsDto patient;
    private String clinicianId;
    private String complaint;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String findings;
    private List<HabitDto> currentHabits;
    private boolean updated;
    private List<ReviewerDetailsDto> reviewers;
    private List<String> reviews;
    private List<ObjectId> images;
    private List<ObjectId> reports;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public TeleconEntryDto(TeleconEntry teleconEntry, PatientDetailsDto patient, List<ReviewerDetailsDto> reviewer) {
        this.id = teleconEntry.getId().toString();
        this.patient = patient;
        this.clinicianId = teleconEntry.getClinicianId().toString();
        this.complaint = teleconEntry.getComplaint();
        this.startTime = teleconEntry.getStartTime();
        this.endTime = teleconEntry.getEndTime();
        this.findings = teleconEntry.getFindings();
        this.currentHabits = teleconEntry.getCurrentHabits();
        this.updated = teleconEntry.isUpdated();
        this.reviewers = reviewer;
        this.reviews = teleconEntry.getReviews();
        this.images = teleconEntry.getImages();
        this.reports = teleconEntry.getReports();
        this.createdAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.updatedAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}