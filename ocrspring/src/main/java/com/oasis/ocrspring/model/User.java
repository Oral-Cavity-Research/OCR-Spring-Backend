package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {

    @Id
    private String id; // ObjectId field

    private String username;
    private String email;
    @Field("reg_no")
    private String regNo; // Updated variable name
    private String hospital;
    private String designation;
    private String contact_no;
    private String password;
    private boolean availability;
    private String role;
    private String createdAt;
    private String updatedAt;

    // Default constructor
    public User() {
    }

    // Constructor with parameters
    public User( String username, String email, String regNo, String hospital, String designation, String contact_no, String password, boolean availability, String role) {

        this.username = username;
        this.email = email;
        this.regNo = regNo; // Updated variable name
        this.hospital = hospital;
        this.designation = designation;
        this.contact_no = contact_no;
        this.password = password;
        this.availability = availability;
        this.role = role;
    }

    // Getters and setters


    public String getId() {
        return id;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

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

    public String getRegNo() { // Updated method name
        return regNo;
    }

    public void setRegNo(String regNo) { // Updated method name
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
                ", regNo='" + regNo + '\'' + // Updated variable name
                ", hospital='" + hospital + '\'' +
                ", designation='" + designation + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", password='" + password + '\'' +
                ", availability=" + availability +
                ", role='" + role + '\'' +
                '}';
    }
}