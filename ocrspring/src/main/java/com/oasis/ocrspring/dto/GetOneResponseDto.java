package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Request;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

public class GetOneResponseDto {
    private String id;
    private String username;
    private String email;
    @Field("reg_no")
    private String regNo;
    private String hospital;
    private String designation;
    private String contact_no;

    public GetOneResponseDto(Request request) {
        this.id = request.getId().toString() ;
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.regNo = request.getRegNo();
        this.hospital = request.getHospital();
        this.designation = request.getDesignation();
        this.contact_no = request.getContact_no();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
