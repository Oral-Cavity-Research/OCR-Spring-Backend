package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.dto.subdto.HabitDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientTeleconRequest {
    private String startTime;
    private String endTime;
    private String complaint;
    private String findings;
    private List<HabitDto> currentHabits;
}