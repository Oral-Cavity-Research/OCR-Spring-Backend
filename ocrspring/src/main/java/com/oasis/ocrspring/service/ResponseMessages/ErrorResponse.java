package com.oasis.ocrspring.service.ResponseMessages;

public class ErrorResponse {
    private boolean success;
    private String message;

    public ErrorResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
