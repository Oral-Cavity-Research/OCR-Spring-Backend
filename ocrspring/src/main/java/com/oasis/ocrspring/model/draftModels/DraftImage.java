package com.oasis.ocrspring.model.draftModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "draftimages")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DraftImage {
    @Id
    private String id;

    @Field("telecon_entry_id")
    private String teleconEntryId;

    @Field("image_name")
    private String imageName;

    private String location;

    @Field("clinical_diagnosis")
    private String clinicalDiagnosis;

    @Field("lesions_appear")
    private Boolean lesionsAppear;

    private List<String> annotation;

    @Field("predicted_cat")
    private String predictedCat;

    public DraftImage(String teleconEntryId, String imageName, String location,
                      String clinicalDiagnosis, Boolean lesionsAppear,
                      List<String> annotation, String predictedCat) {
        this.teleconEntryId = teleconEntryId;
        this.imageName = imageName;
        this.location = location;
        this.clinicalDiagnosis = clinicalDiagnosis;
        this.lesionsAppear = lesionsAppear;
        this.annotation = annotation;
        this.predictedCat = predictedCat;
    }
}