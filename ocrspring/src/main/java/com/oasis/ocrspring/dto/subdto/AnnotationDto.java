package com.oasis.ocrspring.dto.subdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDto {
    private Integer id;
    private String name ;
    private String Lesion;
    private List<Integer> annotations;
    private List<Integer> bbox;
}
