package com.oasis.ocrspring.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Document(collection = "patients")
public class Patient {

    @Id
    @Field("_id")
    private ObjectId id;
    @Field("patient_id")

    private String patientId;


    @Field("clinician_id")
    private ObjectId clinicianId;

    private String patient_name;

    private List<String> risk_factors;

    private Date DOB; // changed to camelCase

    private String gender;

    private String histo_diagnosis;

    private List<String> medical_history;

    private List<String> family_history;

    private String systemic_disease;

    private String contact_no;

    private String consent_form;
    private String createdAt;
    private String updatedAt;

    // Default constructor
    public Patient() {
    }

    // Constructor with parameters
    public Patient(String patient_id, ObjectId clinician_id, String patient_name,
                   List<String> risk_factors, Date DOB, String gender,
                   String histo_diagnosis, List<String> medical_history,
                   List<String> family_history, String systemic_disease,
                   String contact_no, String consent_form,String createdAt,String updatedAt ) {
        this.patientId = patient_id;
        this.clinicianId = clinician_id;
        this.patient_name = patient_name;
        this.risk_factors = risk_factors;
        this.DOB = DOB;
        this.gender = gender;
        this.histo_diagnosis = histo_diagnosis;
        this.medical_history = medical_history;
        this.family_history = family_history;
        this.systemic_disease = systemic_disease;
        this.contact_no = contact_no;
        this.consent_form = consent_form;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getpatientId() {
        return patientId;
    }

    public void setpatientId(String patient_id) {
        this.patientId = patient_id;
    }

    public ObjectId getclinicianId() {
        return clinicianId;
    }

    public void setclinicianId(ObjectId clinician_id) {
        this.clinicianId = clinician_id;
    }

    public String getpatient_name() {
        return patient_name;
    }

    public void setpatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public List<String> getrisk_factors() {
        return risk_factors;
    }

    public void setrisk_factors(List<String> risk_factors) {
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

    public String gethisto_diagnosis() {
        return histo_diagnosis;
    }

    public void sethisto_diagnosis(String histo_diagnosis) {
        this.histo_diagnosis = histo_diagnosis;
    }

    public List<String> getmedical_history() {
        return medical_history;
    }

    public void setmedical_history(List<String> medical_history) {
        this.medical_history = medical_history;
    }

    public List<String> getfamily_history() {
        return family_history;
    }

    public void setfamily_history(List<String> family_history) {
        this.family_history = family_history;
    }

    public String getsystemic_disease() {
        return systemic_disease;
    }

    public void setsystemic_disease(String systemic_disease) {
        this.systemic_disease = systemic_disease;
    }

    public String getcontact_no() {
        return contact_no;
    }

    public void setcontact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getconsent_form() {
        return consent_form;
    }

    public void setconsent_form(String consent_form) {
        this.consent_form = consent_form;
    }

    public ObjectId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", clinicianId='" + clinicianId + '\'' +
                ", patient_name='" + patient_name + '\'' +
                ", risk_factors=" + risk_factors +
                ", DOB=" + DOB +
                ", gender='" + gender + '\'' +
                ", histo_diagnosis='" + histo_diagnosis + '\'' +
                ", medical_history=" + medical_history +
                ", family_history=" + family_history +
                ", systemic_disease='" + systemic_disease + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", consent_form='" + consent_form + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
