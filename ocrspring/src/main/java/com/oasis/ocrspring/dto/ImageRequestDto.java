package com.oasis.ocrspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {
    private String teleconId;
    private String imageName;
    private String location;
    private String clinicalDiagnosis;
    private Boolean lesionsAppear;
    private List<String> annotation;
    private String predictedCat;
}