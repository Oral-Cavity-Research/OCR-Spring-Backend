package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.dto.subdto.Risk_factors;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetailsResDto {
    private String systemicDisease;
    private String id;
    private String patientId;
    private String clinicianId;
    private String patientName;
    private List<Risk_factors> riskFactors;
    private String gender;
    private String histoDiagnosis;
    private LocalDateTime dob;
    private List<String> medicalHistory;
    private List<String> familyHistory;
    private String contactNo;
    private String consentForm;
    private String  createdAt;
    private String updatedAt;



}
