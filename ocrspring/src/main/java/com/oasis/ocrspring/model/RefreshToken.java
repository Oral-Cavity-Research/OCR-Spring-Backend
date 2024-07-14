package com.oasis.ocrspring.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "refreshtokens")
public class RefreshToken {
    @Id
    private String id;
    private String user;
    private String token;
    private LocalDateTime expiresAt;
    private String createdByIP;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime revokedAt;
    private String revokedByIP;
    private String replacedByToken;


    public RefreshToken(String user, String token, LocalDateTime expiresAt, String createdByIP, LocalDateTime createdAt, LocalDateTime revokedAt, String revokedByIP, String replacedByToken) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdByIP = createdByIP;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        this.revokedByIP = revokedByIP;
        this.replacedByToken = replacedByToken;
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

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getcreatedByIP() {
        return createdByIP;
    }

    public void setcreatedByIPIP(String createdByIP) {
        createdByIP = createdByIP;
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

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", expiresAt=" + expiresAt +
                ", createdByIP='" + createdByIP + '\'' +
                ", createdAt=" + createdAt +
                ", revokedAt=" + revokedAt +
                ", revokedByIP='" + revokedByIP + '\'' +
                ", replacedByToken='" + replacedByToken + '\'' +
                '}';
    }
}
