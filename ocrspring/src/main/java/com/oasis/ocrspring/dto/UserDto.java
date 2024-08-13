package com.oasis.ocrspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    private String id; // ObjectId field
    private String username;
    private String hospital;
    private String contactNo;
    private boolean availability;
}