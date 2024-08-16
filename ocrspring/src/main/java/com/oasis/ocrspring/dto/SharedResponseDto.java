package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.dto.subdto.Risk_factors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

public class SharedResponseDto {

    private String  _id;
    private String patient_id;
    private String  clinician_id;
    private String patient_name;
    private List<Risk_factors> risk_factors;
    private String  DOB;
    private String gender;
    private String histo_diagnosis;
    private List<String> medical_history;
    private List<String> family_history;
    private String systemic_disease;
    private String contact_no;
    private String consent_form;
    private String createdAt;
    private String updatedAt;
}
