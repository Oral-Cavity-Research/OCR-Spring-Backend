package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "reports")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Report {
    @Id
    private String id;

    @Field("telecon_id")
    private String teleconId;

    @Field("report_name")
    private String reportName;

    private String createdAt;

    private String updatedAt;

    public Report(String teleconId, String reportName) {
        this.teleconId = teleconId;
        this.reportName = reportName;
    }
}