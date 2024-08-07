package com.oasis.ocrspring.dto;

import java.util.Date;
import java.util.List;

public class ConsentRequestDto {
    private String patient_id;
    private String clinician_id;
    private String patient_name;
    private List<String> risk_factors;
    private Date DOB;
    private String gender;
    private String histo_diagnosis;
    private List<String> medical_history;
    private List<String> family_history;
    private String systemic_disease;
    private String contact_no;
    private String consent_form;

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(String clinician_id) {
        this.clinician_id = clinician_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public List<String> getRisk_factors() {
        return risk_factors;
    }

    public void setRisk_factors(List<String> risk_factors) {
        this.risk_factors = risk_factors;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHisto_diagnosis() {
        return histo_diagnosis;
    }

    public void setHisto_diagnosis(String histo_diagnosis) {
        this.histo_diagnosis = histo_diagnosis;
    }

    public List<String> getMedical_history() {
        return medical_history;
    }

    public void setMedical_history(List<String> medical_history) {
        this.medical_history = medical_history;
    }

    public List<String> getFamily_history() {
        return family_history;
    }

    public void setFamily_history(List<String> family_history) {
        this.family_history = family_history;
    }

    public String getSystemic_disease() {
        return systemic_disease;
    }

    public void setSystemic_disease(String systemic_disease) {
        this.systemic_disease = systemic_disease;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getConsent_form() {
        return consent_form;
    }

    public void setConsent_form(String consent_form) {
        this.consent_form = consent_form;
    }
}
//{
//    "patient_id": "P56",
//    "clinician_id": "641060a61530810142e045de",
//    "patient_name": "John Doe",
//    "risk_factors": ["Smoking", "Alcohol consumption"],
//    "DOB": "1985-07-20T00:00:00.000Z",
//    "gender": "Male",
//    "histo_diagnosis": "Squamous cell carcinoma",
//    "medical_history": ["Hypertension", "Diabetes"],
//    "family_history": ["Father had lung cancer", "Mother had breast cancer"],
//    "systemic_disease": "Diabetes",
//    "contact_no": "+94123456789",
//    "consent_form": "consent_form_123456.pdf"
//}
