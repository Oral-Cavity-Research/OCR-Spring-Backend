package com.oasis.ocrspring.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class UserDto {
    @Id
    private String id; // ObjectId field

    private String username;

    private String hospital;
    private String contact_no;

    private boolean availability;

    public UserDto(String id, String username, String hospital, String contact_no, boolean availability) {
        this.id = id;
        this.username = username;
        this.hospital = hospital;
        this.contact_no = contact_no;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHospital() {
        return hospital;
    }

    public String getContact_no() {
        return contact_no;
    }

    public boolean isAvailability() {
        return availability;
    }
}
