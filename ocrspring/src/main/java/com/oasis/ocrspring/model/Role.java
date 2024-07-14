package com.oasis.ocrspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection ="roles")
public class Role {
    @Id
    private String id;
    private String role;
    private List<String> permissions=new ArrayList<>();

    public Role( String role, List<String> permissions) {

        this.role = role;
        this.permissions = permissions;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
