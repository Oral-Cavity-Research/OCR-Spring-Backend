package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Image;

import java.util.List;

public class UploadImageResponse {
    private List<Image> docs;
    private String message;
    public UploadImageResponse(List<Image> images,String Message){
        this.docs = images;
        this.message =Message;
    }

    public List<Image> getDocs() {
        return docs;
    }

    public void setDocs(List<Image> docs) {
        this.docs = docs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
