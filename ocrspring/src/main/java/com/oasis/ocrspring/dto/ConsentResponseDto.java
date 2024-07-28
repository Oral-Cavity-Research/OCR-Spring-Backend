package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Patient;

import java.util.List;

public class ConsentResponseDto {
    private Patient docs;
    public ConsentResponseDto(Patient patientDetails){
        this.docs = patientDetails;
    }

    public Patient getNewPatient() {
        return docs;
    }

    public void setNewPatient(Patient newPatient) {
        this.docs = newPatient;
    }
}
