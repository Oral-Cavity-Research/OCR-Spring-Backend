package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    private String id;

    @Field("reviewer_id")
    private String reviewerId;

    @Field("telecon_entry")
    private String teleconEntry;

    private Boolean checked;

    private Boolean reviewed;

}
