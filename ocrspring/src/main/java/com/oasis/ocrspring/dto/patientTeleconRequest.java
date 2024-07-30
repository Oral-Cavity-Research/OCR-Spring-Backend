package com.oasis.ocrspring.dto;

import java.time.LocalDateTime;
import java.util.List;

public class patientTeleconRequest {
    private String start_time;
    private String end_time;
    private String complaint;
    private String findings;
    private List<HabitDto> current_habits;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
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

    @Override
    public String toString() {
        return "patientTeleconRequest{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", complaint='" + complaint + '\'' +
                ", findings='" + findings + '\'' +
                ", current_habits=" + current_habits +
                '}';
    }
}
