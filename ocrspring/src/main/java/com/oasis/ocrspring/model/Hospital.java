package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitals")
public class Hospital {
    @Id
    private Long id;
    private String category;
    private String city;
    private String address;
    private String contact_no;

    public Hospital(String category, String city, String address, String contact_no) {
        this.category = category;
        this.city = city;
        this.address = address;
        this.contact_no = contact_no;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", contact_no='" + contact_no + '\'' +
                '}';
    }
}
