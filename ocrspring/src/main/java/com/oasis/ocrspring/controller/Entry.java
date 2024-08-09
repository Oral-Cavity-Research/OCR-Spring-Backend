package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.patientTeleconRequest;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.service.TeleconEntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/entry")
public class Entry {
    @Autowired
    private TeleconEntriesService teleconService;

    // connect entry to the service layer
    @ApiIgnore
    @RequestMapping(value ="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    //add a teleconsultation entry
    @PostMapping("/add/{id}")
    public ResponseEntity<?> addTeleconsultationEntry(@PathVariable String id,
                                                      @RequestHeader("_id") String clinician_id,
                                                      @RequestBody patientTeleconRequest newPatient) {
        // add a teleconsultation entry
        return teleconService.patientTeleconEntry(id,clinician_id,newPatient);
    }

    //get all entries
    @GetMapping("/get")
    public ResponseEntity<?> getAllEntries(@RequestHeader("id") String id,
                                                            @RequestParam(name = "page",required = false,defaultValue = "1" ) Integer page,
                                                            @RequestParam(name = "filter",required = false,defaultValue = "Created Date") String filter) { //id is clinician Id
        // get all teleconsultation entries
        int pageSize = 20;

        return teleconService.getAllUserEntries(id,page,filter,pageSize);
    }

    //get patient entries
    @GetMapping("/get/patient/{id}")
    public ResponseEntity<?> getPatientEntries(@RequestHeader("_id") String clinicianId,
                                    @PathVariable String id,
                                    @RequestParam(name = "page",required = false,defaultValue ="1") Integer page,
                                    @RequestParam(name = "filter",required = false,defaultValue = "1") String filter) {
        int pageSize = 20;
        // get patient entries
        return teleconService.getUserEntryById(clinicianId,id,page,filter,pageSize);
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
    @GetMapping("/count/newreviews")
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

