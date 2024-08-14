package com.oasis.ocrspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {
    @Id
    private String id;

    private String role;

    private List<String> permissions = new ArrayList<>();

    public Role(String role, List<String> permissions) {
        this.role = role;
        this.permissions = permissions;
    }
}
