package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "assignments")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Assignment {
    @Id
    @Field("_id")
    private ObjectId id;

    @Field("reviewer_id")
    private ObjectId reviewerId;

    @Field("telecon_entry")
    private ObjectId teleconEntry;

    private Boolean checked;

    private Boolean reviewed;

    public Assignment(ObjectId reviewerId, ObjectId teleconEntry, Boolean checked,
                      Boolean reviewed) {
        this.reviewerId = reviewerId;
        this.teleconEntry = teleconEntry;
        this.checked = checked;
        this.reviewed = reviewed;
    }
}
