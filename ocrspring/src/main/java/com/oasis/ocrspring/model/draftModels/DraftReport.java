package com.oasis.ocrspring.model.draftModels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "draftreports")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DraftReport
{
    @Id
    private String id;

    @Field("telecon_entry_id")
    private String teleconEntryId;

    @Field("report_name")
    private String reportName;

    public DraftReport(String teleconEntryId, String reportName)
    {
        this.teleconEntryId = teleconEntryId;
        this.reportName = reportName;
    }
}