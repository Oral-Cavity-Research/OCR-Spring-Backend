package com.oasis.ocrspring.model.draftModels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "draftentries")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DraftEntry
{
    @Id
    private String id;

    @Field("patient_id")
    private String patientId;

    @Field("clinician_id")
    private String clinicianId;

    private String complaint;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("end_time")
    private LocalDateTime endTime;

    private String findings;

    @Field("current_habits")
    private List<String> currentHabits;

    private boolean updated;

    public DraftEntry(String patientId, String clinicianId, String complaint,
                      LocalDateTime startTime, LocalDateTime endTime, String findings,
                      List<String> currentHabits, boolean updated)
    {
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.complaint = complaint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.findings = findings;
        this.currentHabits = currentHabits;
        this.updated = updated;
    }
}