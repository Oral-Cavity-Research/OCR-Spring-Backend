package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="options")
public class Option {
    @Id
    private String id;
    private String name;
    private List<String> options;

    public Option(String name, List<String> options) {
        this.name = name;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getoptions() {
        return options;
    }

    public void setoptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "options{" +
                "name='" + name + '\'' +
                ", options=" + options +
                '}';
    }
}
