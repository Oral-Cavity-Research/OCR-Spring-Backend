package com.oasis.ocrspring.model;

public class User {
    private String email;
    private String username;
    private String regNo;
    private String contactNo;
    private String hospital;

    public User() {
    }
    public User (String email, String username, String regNo, String contactNo, String hospital) {
        this.email = email;
        this.username = username;
        this.regNo = regNo;
        this.contactNo = contactNo;
        this.hospital = hospital;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }



    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Override
    public String toString() {
        return "userDetails{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", regNo='" + regNo + '\'' +
                ", contactNo=" + contactNo +
                ", hospital='" + hospital + '\'' +
                '}';
    }
}
