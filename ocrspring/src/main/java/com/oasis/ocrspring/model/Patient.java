package com.oasis.ocrspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @Field("_id")
    @JsonIgnore
    private ObjectId id;
    @JsonProperty("_id")
    public String getIdString(){
        return (id != null)?id.toHexString():null;
    }

    @Field("patient_id")
    private String patientId;

    @Field("clinician_id")
    @JsonIgnore
    private ObjectId clinicianId;
    @JsonProperty("clinician_id")
    public String getPatientIdString(){
        return (clinicianId != null)?clinicianId.toHexString():null;
    }

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

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + getIdString() + '\'' +
                ", patientId='" + getPatientIdString() + '\'' +
                ", clinicianId='" + clinicianId + '\'' +
                ", patient_name='" + patientName + '\'' +
                ", risk_factors=" + riskFactors +
                ", DOB=" + dob +
                ", gender='" + gender + '\'' +
                ", histo_diagnosis='" + histoDiagnosis + '\'' +
                ", medical_history=" + medicalHistory +
                ", family_history=" + familyHistory +
                ", systemic_disease='" + systemicDisease + '\'' +
                ", contact_no='" + contactNo + '\'' +
                ", consent_form='" + consentForm + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
