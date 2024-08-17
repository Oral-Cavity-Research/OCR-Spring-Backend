package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.Review;

public class RequestResDetailsDto {
    private String _id;
    private String username;
    private String reg_no;
    private String hospital;

    public RequestResDetailsDto(Request request){
        this._id =request.getId();
        this.username =request.getUserName();
        this.reg_no = request.getRegNo();
        this.hospital=request.getHospital();
    }
}
