package com.oasis.ocrspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/entry")
public class Entry {

    // connect entry to the service layer
    @ApiIgnore
    @RequestMapping(value ="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    //add a teleconsultation entry
    @PostMapping("/add/{id}")
    public String addTeleconsultationEntry(long id) {
        // add a teleconsultation entry
        return "/api/user/entry/add/"+id;
    }

    //get all entries
    @GetMapping("/get")
    public String getAllEntries() {
        // get all teleconsultation entries
        return "/api/user/entry/get";
    }

    //get patient entries
    @GetMapping("/get/patient/{id}")
    public String getPatientEntries(long id) {
        // get patient entries
        return "/api/user/entry/get/patient/"+id;
    }

    //get shared patient entries (view only data)
    //id is the patient id
    @GetMapping("/shared/patient/{id}")
    public String getSharedPatientEntries(long id) {
        // get shared patient entries
        return "/api/user/entry/shared/patient/"+id;
    }

    // get one entry details added by users
    // id is entry _id
    @GetMapping("/get/{id}")
    public String getEntry(long id) {
        // get one entry
        return "/api/user/entry/get/"+id;
    }

    //get new review count
    @GetMapping("/vount/newreviews")
    public String getNewReviewCount() {
        // get new review count
        return "/api/user/entry/vount/newreviews";
    }

    //get unreviewed entry count
    @GetMapping("/count/newentries")
    public String getUnreviewedEntryCount() {
        // get unreviewed entry count
        return "/api/user/entry/count/newentries";
    }

    //add a reviewer by user
    //id is entry _id
    @PostMapping("/reviewer/add/{id}")
    public String addReviewer(long id) {
        // add a reviewer
        return "/api/user/entry/reviewer/add/"+id;
    }

    //remove a reviewer by user
    //id is entry _id
    @PostMapping("/reviewer/remove/{id}")
    public String removeReviewer(long id) {
        // remove a reviewer
        return "/api/user/entry/reviewer/remove/"+id;
    }

    //delete an entry by user
    //id is entry _id
    @PostMapping("/delete/{id}")
    public String deleteEntry(long id) {
        // delete an entry
        return "/api/user/entry/delete/"+id;
    }

    //get all shared entries
    @GetMapping("/shared/all")
    public String getAllSharedEntries() {
        // get all shared entries
        return "/api/user/entry/shared/all";
    }

    //get one shared entry(view only)
    //id is entry _id
    @GetMapping("/shared/{id}")
    public String getSharedEntry(long id) {
        // get one shared entry
        return "/api/user/entry/shared/"+id;
    }

    //get assigned entry details
    //id is assignment _id
    @GetMapping("/shared/data/{id}")
    public String getAssignedEntryDetails(long id) {
        // get assigned entry details
        return "/api/user/entry/shared/data/"+id;
    }

    //get entry reviews
    //id is entry _id
    @GetMapping("/reviews/{id}")
    public String getEntryReviews(long id) {
        // get entry reviews
        return "/api/user/entry/reviews/"+id;
    }

    //change a reviewer(reviewer assignes another)
    @PostMapping("/reviewer/change/{id}")
    public String changeReviewer(long id) {
        // change a reviewer
        return "/api/user/entry/reviewer/change/"+id;
    }

    //add new review
    //id is telecon_id
    @PostMapping("/review/{id}")
    public String addNewReview(long id) {
        // add new review
        return "/api/user/entry/review/"+id;
    }

    //mark as read
    //id is assignment _id
    @PostMapping("/mark/{id}")
    public String markAsRead(long id) {
        // mark as read
        return "/api/user/entry/mark/"+id;
    }

    //mark as read
    //id is entry _id
    @PostMapping("/open/{id}")
    public String markAsOpen(long id) {
        // mark as open
        return "/api/user/entry/open/"+id;
    }
}

