package com.oasis.ocrspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oasis.ocrspring.dto.subdto.HabitDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientTeleconRequest {
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    private String complaint;
    private String findings;
    @JsonProperty("current_habits")
    private List<HabitDto> currentHabits;
}