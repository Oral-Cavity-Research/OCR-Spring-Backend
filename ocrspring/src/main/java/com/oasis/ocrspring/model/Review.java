package com.oasis.ocrspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @Field("_id")
    @JsonIgnore
    private ObjectId id;
    @JsonProperty("_id")
    public String getIDString(){return (id != null)?id.toHexString():null;}

    @Field("telecon_entry_id")
    @JsonIgnore
    private ObjectId teleconEntryId;
    @JsonProperty("telecon_entry_id")
    public String getTeleconId(){return ((teleconEntryId != null)?teleconEntryId.toHexString():null);}

    @Field("reviewer_id")
    @JsonIgnore
    private ObjectId reviewerId;
    @JsonProperty("reviewer_id")
    public String getReviewerId(){return ((reviewerId != null)?reviewerId.toHexString():null);}

    @Field("provisional_diagnosis")
    private String provisionalDiagnosis;

    @Field("management_suggestions")
    private String managementSuggestions;

    @Field("referral_suggestions")
    private String referralSuggestions;

    @Field("other_comments")
    private String otherComments;

    @CreatedDate
    @Field("createdAt")
    private LocalDateTime createdAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    @LastModifiedDate
    @Field("updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    @Override
    public String toString() {
        return "Review{" +
                "id=" + getIDString() +
                ", teleconEntryId=" + getTeleconId() +
                ", reviewerId=" + getReviewerId() +
                ", provisionalDiagnosis='" + provisionalDiagnosis + '\'' +
                ", managementSuggestions='" + managementSuggestions + '\'' +
                ", referralSuggestions='" + referralSuggestions + '\'' +
                ", otherComments='" + otherComments + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}