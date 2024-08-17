package com.oasis.ocrspring.dto;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String complaint;
    private String findings;
    private List<HabitDto> currentHabits;
}