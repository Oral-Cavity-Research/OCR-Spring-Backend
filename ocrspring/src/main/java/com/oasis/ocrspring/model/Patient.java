package com.oasis.ocrspring.model;

import com.oasis.ocrspring.dto.subdto.Risk_factors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "patients")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Patient {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field("patient_id")
    private String patientId;

    @Field("clinician_id")
    private ObjectId clinicianId;

    @Field("patient_name")
    private String patientName;

    @Field("risk_factors")
    private List<Risk_factors> riskFactors;

    @Field("DOB")
    private LocalDateTime dob;

    private String gender;

    @Field("histo_diagnosis")
    private String histoDiagnosis;

    @Field("medical_history")
    private List<String> medicalHistory;

    @Field("family_history")
    private List<String> familyHistory;

    @Field("systemic_disease")
    private String systemicDisease;

    @Field("contact_no")
    private String contactNo;

    @Field("consent_form")
    private String consentForm;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Patient(String patientId, ObjectId clinicianId, String patientName,
                   List<Risk_factors> riskFactors, LocalDateTime dob, String gender,
                   String histoDiagnosis, List<String> medicalHistory,
                   List<String> familyHistory, String systemicDisease,
                   String contactNo, String consentForm, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.patientName = patientName;
        this.riskFactors = riskFactors;
        this.dob = dob;
        this.gender = gender;
        this.histoDiagnosis = histoDiagnosis;
        this.medicalHistory = medicalHistory;
        this.familyHistory = familyHistory;
        this.systemicDisease = systemicDisease;
        this.contactNo = contactNo;
        this.consentForm = consentForm;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("_id", this.getId());
        map.put("patient_id", this.getPatientId());
        map.put("clinician_id", this.getClinicianId());
        map.put("patient_name", this.getPatientName());
        map.put("risk_factors", this.getRiskFactors());
        map.put("DOB", this.getDob());
        map.put("gender", this.getGender());
        map.put("histo_diagnosis", this.getHistoDiagnosis());
        map.put("medical_history", this.getMedicalHistory());
        map.put("family_history", this.getFamilyHistory());
        map.put("systemic_disease", this.getSystemicDisease());
        map.put("contact_no", this.getContactNo());
        map.put("consent_form", this.getConsentForm());
        map.put("createdAt", this.getCreatedAt());
        map.put("updatedAt", this.getUpdatedAt());
        return map;
    }
}