package com.oasis.ocrspring.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "patients")
public class Patient {

    @Id
    private ObjectId id;
    @Field("patient_id")
    private String patientId;
    @Field("clinician_id")
    private ObjectId clinicianId;

    @Field("patient_name")
    private String patientName;

    @Field("risk_factors")
    private List<String> riskFactors;

    private LocalDateTime DOB; // changed to camelCase

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
    // Default constructor
    public Patient() {
    }

    public Patient(String patientId, ObjectId clinicianId, String patientName, List<String> riskFactors, LocalDateTime DOB, String gender, String histoDiagnosis, List<String> medicalHistory, List<String> familyHistory, String systemicDisease, String contactNo, String consentForm) {
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.patientName = patientName;
        this.riskFactors = riskFactors;
        this.DOB = DOB;
        this.gender = gender;
        this.histoDiagnosis = histoDiagnosis;
        this.medicalHistory = medicalHistory;
        this.familyHistory = familyHistory;
        this.systemicDisease = systemicDisease;
        this.contactNo = contactNo;
        this.consentForm = consentForm;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
// Constructor with parameters

    // Getters and Setters

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public ObjectId getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(ObjectId clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public List<String> getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(List<String> riskFactors) {
        this.riskFactors = riskFactors;
    }

    public LocalDateTime getDOB() {
        return DOB;
    }

    public void setDOB(LocalDateTime DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHistoDiagnosis() {
        return histoDiagnosis;
    }

    public void setHistoDiagnosis(String histoDiagnosis) {
        this.histoDiagnosis = histoDiagnosis;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<String> getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(List<String> familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getSystemicDisease() {
        return systemicDisease;
    }

    public void setSystemicDisease(String systemicDisease) {
        this.systemicDisease = systemicDisease;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getConsentForm() {
        return consentForm;
    }

    public void setConsentForm(String consentForm) {
        this.consentForm = consentForm;
    }

    public ObjectId getId() {
        return id;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("_id", this.getId());
        map.put("patient_id", this.getPatientId());
        map.put("clinician_id", this.getClinicianId());
        map.put("patient_name", this.getPatientName());
        map.put("risk_factors", this.getRiskFactors());
        map.put("DOB", this.getDOB());
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
