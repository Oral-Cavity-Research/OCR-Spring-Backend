package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.Request;
import org.bson.types.ObjectId;

public class RequestResponseDto {
    private String _id;
    private String username;
    private String reg_no;
    private String hospital;

    public RequestResponseDto(Request request) {
        this._id = request.getId().toString();
        this.username = request.getUsername();
        this.reg_no = request.getRegNo();
        this.hospital =request.getHospital();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
