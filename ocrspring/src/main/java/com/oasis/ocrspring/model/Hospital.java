package com.oasis.ocrspring.model;

import com.oasis.ocrspring.dto.HospitalDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Hospital(HospitalDto hospitalDto) {
        this.name = hospitalDto.getName();
        this.category = hospitalDto.getCategory();
        this.city = hospitalDto.getCity();
        this.address = hospitalDto.getAddress();
        this.contactNo = hospitalDto.getContact_no();
        this.createdAt = LocalDateTime.now();
        }

    public void setHospital(HospitalDto hospitalDto) {
        this.name = hospitalDto.getName();
        this.category = hospitalDto.getCategory();
        this.city = hospitalDto.getCity();
        this.address = hospitalDto.getAddress();
        this.contactNo = hospitalDto.getContact_no();
        this.updatedAt = LocalDateTime.now();
    }


}