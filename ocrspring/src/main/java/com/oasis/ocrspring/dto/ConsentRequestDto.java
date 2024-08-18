package com.oasis.ocrspring.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.oasis.ocrspring.dto.subdto.Risk_factors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRequestDto {
    private String patient_id;
    private String clinician_id;
    private String patient_name;
    private List<Risk_factors> risk_factors;
    @JsonProperty("DOB")
    private String DOB;
    private String gender;
    private String histo_diagnosis;
    private List<String> medical_history;
    private List<String> family_history;
    private String systemic_disease;
    private String contact_no;
    private String consent_form;

}