package com.oasis.ocrspring.model;

import lombok.*;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @Field("_id")
    private ObjectId id;

    @Field("telecon_entry_id")
    private ObjectId teleconEntryId;

    @Field("reviewer_id")
    private ObjectId reviewerId;

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
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    private LocalDateTime updatedAt;

//    public Review(ObjectId teleconEntryId, ObjectId reviewerId, String provisionalDiagnosis, String managementSuggestions, String referralSuggestions, String otherComments) {
//        this.teleconEntryId = teleconEntryId;
//        this.reviewerId = reviewerId;
//        this.provisionalDiagnosis = provisionalDiagnosis;
//        this.managementSuggestions = managementSuggestions;
//        this.referralSuggestions = referralSuggestions;
//        this.otherComments = otherComments;
//        this.createdAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        this.updatedAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//
//    }
}