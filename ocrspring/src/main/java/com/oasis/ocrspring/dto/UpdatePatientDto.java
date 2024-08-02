package com.oasis.ocrspring.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UpdatePatientDto {
    private String patientName;
    private String gender;
    private LocalDateTime DOB;
    private List<String> riskFactors;
    private String histoDiagnosis;
    private String contactNo;
    private String systemicDesease;
    private List<String> familyHistory;
    private List<String> medicalHistory;

//    public updatePatientDto(String patientName, String gender, LocalDateTime DOB, List<String> riskFactors, String histoDiagnosis, String contactNo, String systemicDesease, List<String> familyHistory, List<String> medicalHistory) {
//        this.patientName = patientName;
//        this.gender = gender;
//        this.DOB = DOB;
//        this.riskFactors = riskFactors;
//        this.histoDiagnosis = histoDiagnosis;
//        this.contactNo = contactNo;
//        this.systemicDesease = systemicDesease;
//        this.familyHistory = familyHistory;
//        this.medicalHistory = medicalHistory;
//    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getDOB() {
        return DOB;
    }

    public void setDOB(LocalDateTime DOB) {
        this.DOB = DOB;
    }

    public List<String> getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(List<String> riskFactors) {
        this.riskFactors = riskFactors;
    }

    public String getHistoDiagnosis() {
        return histoDiagnosis;
    }

    public void setHistoDiagnosis(String histoDiagnosis) {
        this.histoDiagnosis = histoDiagnosis;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSystemicDesease() {
        return systemicDesease;
    }

    public void setSystemicDesease(String systemicDesease) {
        this.systemicDesease = systemicDesease;
    }

    public List<String> getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(List<String> familyHistory) {
        this.familyHistory = familyHistory;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
