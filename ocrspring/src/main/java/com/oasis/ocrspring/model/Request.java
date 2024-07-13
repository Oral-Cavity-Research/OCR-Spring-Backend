package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
public class Request {
    @Id
    private String id;
    private String username;
    private String email;
    private String reg_no;
    private String hospital;
    private String designation;
    private String contact_no;

    public Request( String username, String email, String reg_no, String hospital, String designation, String contact_no) {

        this.username = username;
        this.email = email;
        this.reg_no = reg_no;
        this.hospital = hospital;
        this.designation = designation;
        this.contact_no = contact_no;
    }



    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Request{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", reg_no='" + reg_no + '\'' +
                ", hospital='" + hospital + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
