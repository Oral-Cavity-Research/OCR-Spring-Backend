package com.oasis.ocrspring.model.draftModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "draftentries")
public class DraftEntry {
    @Id
    private Long id;
    private Long patient_id;
    private Long clinician_id;
    private String complaint;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String findings;
    private List<String> current_habits;
    private boolean updated;

    public DraftEntry(Long patient_id, Long clinician_id, String complaint, LocalDateTime start_time, LocalDateTime end_time, String findings, List<String> current_habits, boolean updated) {
        this.patient_id = patient_id;
        this.clinician_id = clinician_id;
        this.complaint = complaint;
        this.start_time = start_time;
        this.end_time = end_time;
        this.findings = findings;
        this.current_habits = current_habits;
        this.updated = updated;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public Long getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(Long clinician_id) {
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

    @Override
    public String toString() {
        return "DraftEntry{" +
                "patient_id=" + patient_id +
                ", clinician_id=" + clinician_id +
                ", complaint='" + complaint + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", findings='" + findings + '\'' +
                ", current_habits=" + current_habits +
                ", updated=" + updated +
                '}';
    }
}
