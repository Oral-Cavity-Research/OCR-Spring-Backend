package com.oasis.ocrspring.dto;

public class messageDto {
    private String message;

    public messageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "messageDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
