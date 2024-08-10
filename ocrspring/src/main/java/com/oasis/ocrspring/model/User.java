package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
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

    private String createdAt;

    private String updatedAt;

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
}