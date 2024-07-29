package com.oasis.ocrspring.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;



@Document(collection = "refreshtokens")
public class RefreshToken {
    @Id
    private String id;
    private String user;
    private String token;
    private String expiresAt;
    private String createdByIP;

    private String createdAt;
    private String revokedAt;
    private String revokedByIP;
    private String replacedByToken;//


    public RefreshToken(String user, String token, String expiresAt, String createdByIP, String createdAt, String revokedAt, String revokedByIP, String replacedByToken) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdByIP = createdByIP;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        this.revokedByIP = revokedByIP;
        this.replacedByToken = replacedByToken;
    }
    public RefreshToken( ) {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getcreatedByIP() {
        return createdByIP;
    }

    public void setcreatedByIP(String createdByIP) {
        createdByIP = createdByIP;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(String revokedAt) {
        this.revokedAt = revokedAt;
    }

    public String getRevokedByIP() {
        return revokedByIP;
    }

    public void setRevokedByIP(String revokedByIP) {
        this.revokedByIP = revokedByIP;
    }

    public String getReplacedByToken() {
        return replacedByToken;
    }

    public void setReplacedByToken(String replacedByToken) {
        this.replacedByToken = replacedByToken;
    }





}
