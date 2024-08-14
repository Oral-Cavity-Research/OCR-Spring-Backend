package com.oasis.ocrspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportsRequestDto {
    private String teleconId;
    private String reportName;
}