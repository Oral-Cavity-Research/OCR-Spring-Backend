package com.oasis.ocrspring.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;

    private String username;

    private String email;

    @Field("reg_no")
    private String regNo;

    private String hospital;

    private String designation;

    @Field("contact_no")
    private String contactNo;

    private String password;

    private boolean availability;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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


    public User(String username, String email, String regNo, String role, String hospital, String designation, String contactNo, boolean availability) {
        this.username = username;
        this.email = email;
        this.regNo = regNo;
        this.role = role;
        this.hospital = hospital;
        this.designation = designation;
        this.contactNo = contactNo;
        this.availability = availability;
        this.createdAt = LocalDateTime.now();
       // LocalDateTime.parse((LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }
    public boolean isAvailable() {
        return availability;
    }


}