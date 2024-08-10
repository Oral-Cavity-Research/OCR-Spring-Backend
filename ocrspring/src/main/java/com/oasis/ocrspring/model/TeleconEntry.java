package com.oasis.ocrspring.model;

import com.oasis.ocrspring.dto.subdto.HabitDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "teleconentries")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeleconEntry
{
    @Id
    @Field("_id")
    private ObjectId id;

    private ObjectId patient;

    @Field("clinician_id")
    private ObjectId clinicianId;

    private String complaint;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("end_time")
    private LocalDateTime endTime;

    private String findings;

    @Field("current_habits")
    private List<HabitDto> currentHabits;

    private boolean updated;

    private List<String> reviewers;

    private List<String> reviews;

    private List<String> images;

    private List<String> reports;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public TeleconEntry(ObjectId id, ObjectId patient, ObjectId clinicianId,
                        String complaint,
                        LocalDateTime startTime, LocalDateTime endTime, String findings,
                        List<HabitDto> currentHabits, boolean updated,
                        List<String> reviewers, List<String> reviews, List<String> images,
                        List<String> reports, LocalDateTime createdAt,
                        LocalDateTime updatedAt)
    {
        this.id = id;
        this.patient = patient;
        this.clinicianId = clinicianId;
        this.complaint = complaint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.findings = findings;
        this.currentHabits = currentHabits;
        this.updated = updated;
        this.reviewers = reviewers;
        this.reviews = reviews;
        this.images = images;
        this.reports = reports;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}