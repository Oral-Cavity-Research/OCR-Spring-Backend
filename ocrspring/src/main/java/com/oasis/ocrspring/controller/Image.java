package com.oasis.ocrspring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class Image {

    // id is entry _id
    @PostMapping("/update")
    public String updateImage() {
        // update image
        return "/api/image/update";
    }


}
