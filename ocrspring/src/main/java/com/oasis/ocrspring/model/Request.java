package com.oasis.ocrspring.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "requests")
public class Request {
    @Id
    @Field("_id")
    private ObjectId id;
    private String username;
    private String email;
    @Field("reg_no")
    private String regNo;
    private String hospital;
    private String designation;
    private String contact_no;

    public Request(ObjectId id, String username, String email, String regNo, String hospital, String designation, String contact_no) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.regNo = regNo;
        this.hospital = hospital;
        this.designation = designation;
        this.contact_no = contact_no;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", regNo='" + regNo + '\'' +
                ", hospital='" + hospital + '\'' +
                ", designation='" + designation + '\'' +
                ", contact_no='" + contact_no + '\'' +
                '}';
    }
}
