package com.oasis.ocrspring.service.ResponseMessages;

public class ErrorMessage extends Throwable {

    private String message;


    public ErrorMessage(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}