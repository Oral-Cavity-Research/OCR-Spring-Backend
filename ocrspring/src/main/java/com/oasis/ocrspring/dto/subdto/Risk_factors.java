package com.oasis.ocrspring.dto.subdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Risk_factors {
    private String habit;
    private String frequency;
    private String duration;
}