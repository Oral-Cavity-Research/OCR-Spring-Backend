package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

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
    private List<String> riskFactors;

    @Field("DOB")
    private Date dob;

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

    private String createdAt;

    private String updatedAt;

    public Patient(String patientId, ObjectId clinicianId, String patientName,
                   List<String> riskFactors, Date dob, String gender,
                   String histoDiagnosis, List<String> medicalHistory,
                   List<String> familyHistory, String systemicDisease,
                   String contactNo, String consentForm, String createdAt, String updatedAt) {
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
}