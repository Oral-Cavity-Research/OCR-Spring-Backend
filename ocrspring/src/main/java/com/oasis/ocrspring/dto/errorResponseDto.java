package com.oasis.ocrspring.dto;

public class errorResponseDto {
    private String message;
    private String error;
    public errorResponseDto(String message,String error){
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
