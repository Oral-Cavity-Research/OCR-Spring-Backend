package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id; // ObjectId field

    private String username;
    private String email;
    private String reg_no; // Updated variable name
    private String hospital;
    private String designation;
    private String contact_no;
    private String password;
    private boolean availability;
    private String role;

    // Default constructor
    public User() {
    }

    // Constructor with parameters
    public User( String username, String email, String reg_no, String hospital, String designation, String contact_no, String password, boolean availability, String role) {

        this.username = username;
        this.email = email;
        this.reg_no = reg_no; // Updated variable name
        this.hospital = hospital;
        this.designation = designation;
        this.contact_no = contact_no;
        this.password = password;
        this.availability = availability;
        this.role = role;
    }

    // Getters and setters


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

    public String getReg_no() { // Updated method name
        return reg_no;
    }

    public void setReg_no(String reg_no) { // Updated method name
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

    public String getcontact_no() {
        return contact_no;
    }

    public void setcontact_no(String contact_no) {
        this.contact_no = contact_no;
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
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", reg_no='" + reg_no + '\'' + // Updated variable name
                ", hospital='" + hospital + '\'' +
                ", designation='" + designation + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", password='" + password + '\'' +
                ", availability=" + availability +
                ", role='" + role + '\'' +
                '}';
    }
}