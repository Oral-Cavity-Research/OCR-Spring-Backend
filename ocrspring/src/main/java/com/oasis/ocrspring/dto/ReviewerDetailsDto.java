package com.oasis.ocrspring.dto;

import com.oasis.ocrspring.model.User;

public class ReviewerDetailsDto
{
    private String _id;
    private String username;

    public ReviewerDetailsDto(User Reviewer)
    {
        this._id = String.valueOf(Reviewer.getId());
        this.username = Reviewer.getUsername();
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
}
