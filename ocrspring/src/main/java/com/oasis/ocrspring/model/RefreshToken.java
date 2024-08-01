package com.oasis.ocrspring.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Document(collection = "refreshtokens")
public class RefreshToken {
    @Id
    @Field("_id")
    private ObjectId id;
    private ObjectId user;
    private String token;
    private LocalDateTime expiresAt;
    private String createdByIP;

    private LocalDateTime createdAt;
    private LocalDateTime revokedAt;
    private String revokedByIP;
    private String replacedByToken;//


    public RefreshToken(ObjectId user, String token, String createdByIP,  LocalDateTime revokedAt, String revokedByIP, String replacedByToken) {
        this.user = user;
        this.token = token;

        this.createdByIP = createdByIP;

        this.revokedAt = revokedAt;
        this.revokedByIP = revokedByIP;
        this.replacedByToken = replacedByToken;
    }
    public RefreshToken( ) {

    }

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getcreatedByIP() {
        return createdByIP;
    }

    public void setcreatedByIP(String createdByIP) {
        this.createdByIP = createdByIP;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
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

    public boolean isExpired() {
        return this.expiresAt.isBefore(LocalDateTime.now());
    }






}
