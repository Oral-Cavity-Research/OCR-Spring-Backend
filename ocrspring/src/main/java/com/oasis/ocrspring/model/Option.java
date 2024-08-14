package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "options")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Option {
    @Id
    private String id;

    private String name;

    private List<String> options;

    public Option(String name, List<String> options) {
        this.name = name;
        this.options = options;
    }
}