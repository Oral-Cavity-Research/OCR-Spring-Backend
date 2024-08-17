package com.oasis.ocrspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image
{
    @Id
    @Field("_id")
    @JsonIgnore
    private ObjectId id;

    @JsonProperty("_id")
    public String getIdString() {
        return id != null ? id.toHexString() : null;
    }

    @JsonIgnore
    @Field("telecon_entry_id")
    private ObjectId teleconEntryId;

    @JsonProperty("telecon_entry_id")
    public String getTeleconEntryIdString() {
        return teleconEntryId != null ? teleconEntryId.toHexString() : null;
    }

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

    private String createdAt;

    private String updatedAt;

    @Override
    public String toString() {
        return "Image{" +
                "_id='" + getIdString() + '\'' +
                ", telecon_entry_id='" + getTeleconEntryIdString() + '\'' +
                ", image_name='" + imageName + '\'' +
                ", location='" + location + '\'' +
                ", clinical_diagnosis='" + clinicalDiagnosis + '\'' +
                ", lesions_appear=" + lesionsAppear +
                ", annotation=" + annotation +
                ", predicted_cat='" + predictedCat + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}