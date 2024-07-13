package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="options")
public class Option {
    @Id
    private String id;
    private String name;
    private List<String> option;

    public Option(String name, List<String> option) {
        this.name = name;
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", option=" + option +
                '}';
    }
}
