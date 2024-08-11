package com.oasis.ocrspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRequestDto {
    private String patientId;
    private String clinicianId;
    private String patientName;
    private List<String> riskFactors;
    private Date dateOfBirth;
    private String gender;
    private String histoDiagnosis;
    private List<String> medicalHistory;
    private List<String> familyHistory;
    private String systemicDisease;
    private String contactNo;
    private String consentForm;
}