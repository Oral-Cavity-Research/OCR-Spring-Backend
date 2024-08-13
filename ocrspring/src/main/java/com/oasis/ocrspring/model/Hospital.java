package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "hospitals")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Hospital {
    @Id
    private String id;

    private String name;

    private String category;

    private String city;

    private String address;

    @Field("contact_no")
    private String contactNo;

    private String createdAt;

    private String updatedAt;

    public Hospital(String category, String city, String address, String contactNo) {
        this.category = category;
        this.city = city;
        this.address = address;
        this.contactNo = contactNo;
    }
}