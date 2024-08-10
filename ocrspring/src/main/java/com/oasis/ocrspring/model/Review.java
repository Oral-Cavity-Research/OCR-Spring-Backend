package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "reviews")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Review {
    @Id
    private String id;

    @Field("telecon_entry_id")
    private String teleconEntryId;

    @Field("reviewer_id")
    private String reviewerId;

    @Field("provisional_diagnosis")
    private String provisionalDiagnosis;

    @Field("management_suggestions")
    private String managementSuggestions;

    @Field("referral_suggestions")
    private String referralSuggestions;

    @Field("other_comments")
    private String otherComments;

    public Review(String teleconEntryId, String reviewerId, String provisionalDiagnosis, String managementSuggestions, String referralSuggestions, String otherComments) {
        this.teleconEntryId = teleconEntryId;
        this.reviewerId = reviewerId;
        this.provisionalDiagnosis = provisionalDiagnosis;
        this.managementSuggestions = managementSuggestions;
        this.referralSuggestions = referralSuggestions;
        this.otherComments = otherComments;
    }
}