package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Patient;

public class patientDetailsDto {
    private  String _id;
    private String patient_id;
    private String patient_name;
    public patientDetailsDto(Patient patient){
        this._id = patient.getId().toString();
        this.patient_id = patient.getPatientId();
        this.patient_name = patient.getPatientName();

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
}
