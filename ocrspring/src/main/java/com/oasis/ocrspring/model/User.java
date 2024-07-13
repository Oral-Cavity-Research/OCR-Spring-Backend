package com.oasis.ocrspring.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="users")
public class User {
    private String username;
    private String email;
    private String regNo;
    private String hospital;
    private String designation;
    private String contactNo;
    private String password;
    private boolean availability;
    private String role;



    public User() {
    }

    public User(String username, String email, String regNo, String hospital, String designation, String contactNo, String password, boolean availability, String role) {
        this.username = username;
        this.email = email;
        this.regNo = regNo;
        this.hospital = hospital;
        this.designation = designation;
        this.contactNo = contactNo;
        this.password = password;
        this.availability = availability;
        this.role = role;
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

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "userDetails{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", regNo='" + regNo + '\'' +
                ", hospital='" + hospital + '\'' +
                ", designation='" + designation + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", password='" + password + '\'' +
                ", availability=" + availability +
                ", role='" + role + '\'' +
                '}';
    }
}
