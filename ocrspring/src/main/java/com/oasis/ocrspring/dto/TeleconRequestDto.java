package com.oasis.ocrspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class TeleconRequestDto {
    private String telecon_entry_id;
    private String image_name;
    private String location;
    private String clinical_diagnosis;
    private Boolean lesions_appear;
    private List<String> annotation;
    private String predicted_cat;


    public String getTelecon_entry_id() {
        return telecon_entry_id;
    }

    public void setTelecon_entry_id(String telecon_entry_id) {
        this.telecon_entry_id = telecon_entry_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClinical_diagnosis() {
        return clinical_diagnosis;
    }

    public void setClinical_diagnosis(String clinical_diagnosis) {
        this.clinical_diagnosis = clinical_diagnosis;
    }

    public Boolean getLesions_appear() {
        return lesions_appear;
    }

    public void setLesions_appear(Boolean lesions_appear) {
        this.lesions_appear = lesions_appear;
    }

    public List<String> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(List<String> annotation) {
        this.annotation = annotation;
    }

    public String getPredicted_cat() {
        return predicted_cat;
    }

    public void setPredicted_cat(String predicted_cat) {
        this.predicted_cat = predicted_cat;
    }
}
