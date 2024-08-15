package com.oasis.ocrspring.dto;

public class MessageDto
{
    private String message;

    public MessageDto(String message) {
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
