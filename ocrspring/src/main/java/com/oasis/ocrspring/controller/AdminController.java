package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.RequestResDetailsDto;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.service.RequestService;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
import com.oasis.ocrspring.service.ReviewerResDto;
import com.oasis.ocrspring.service.auth.AuthenticationToken;
import com.oasis.ocrspring.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    // connect admin to the service layer
    @Autowired
    TokenService tokenService;
    @Autowired
    RequestService requestService;
    @Autowired
    AuthenticationToken authenticationToken;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    //get all requests
    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequests(HttpServletRequest request,HttpServletResponse response) throws IOException {
        authenticationToken.authenticateRequest(request,response);
        if (!tokenService.checkPermissions(request, List.of("100"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            List<Request> requests = requestService.AllRequestDetails();
            List<RequestResDetailsDto> requestResDetailsDtos = new ArrayList<>();
            for (Request reviewer : requests) {
                requestResDetailsDtos.add(new RequestResDetailsDto(reviewer));
            }
            return ResponseEntity.ok(requestResDetailsDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //get one request
    @GetMapping("/requests/{id}")
    public String getRequest(long id) {
        return "/api/admin/requests/" + id;
    }

    //reject a request
    @PostMapping("/requests/{id}")
    public String rejectRequest(long id) {
        return "/api/admin/requests/" + id;
    }

    //approve a request
    @PostMapping("/accept/{id}")
    public String acceptRequest(long id) {
        return "/api/admin/accept/" + id;
    }

    //get users by thier roles
    //only for read or write access permission
    @GetMapping("/users/roles/{role}")
    public String getUsersByRole(String role) {
        return "/api/admin/users/roles/" + role;
    }

    //get all user roles
    @GetMapping("/roles")
    public String getAllRoles() {
        return "/api/admin/roles";
    }

    //get one user role
    @GetMapping("/roles/{id}")
    public String getRole(long id) {
        return "/api/admin/roles/" + id;
    }

    //add a new role
    @PostMapping("/roles")
    public String addRole() {
        return "/api/admin/roles";
    }

    //update user permission
    @PostMapping("/roles/{id}")
    public String updateRole(long id) {
        return "/api/admin/roles/" + id;
    }

    //get a specific user
    @GetMapping("/users/{id}")
    public String getUser(long id) {
        return "/api/admin/users/" + id;
    }

    //update a user
    @PostMapping("update/users/{id}")
    public String updateUser(@PathVariable long id) {
        return "/api/admin/update/users/" + id;
    }

    //delete a user
    @PostMapping("delete/users/{id}")
    public String deleteUser(long id) {
        return "/api/admin/delete/users/" + id;
    }

    //add hospital
    @PostMapping("/hospital")
    public String addHospital() {
        return "/api/admin/hospital";
    }

    //update hospital details
    @PostMapping("/hospitals/update/{id}")
    public String updateHospital(long id) {
        return "/api/admin/hospitals/update/" + id;
    }

    //delete hospital
    @PostMapping("/hospitals/delete/{id}")
    public String deleteHospital(long id) {
        return "/api/admin/hospitals/delete/" + id;
    }

    //get all hospitals
    @GetMapping("/hospitals/{id}")
    public String getHospital(long id) {
        return "/api/admin/hospitals/" + id;
    }

    //get options (labels and values)
    @GetMapping("/options/{name}")
    public String getOptions(String name) {
        return "/api/admin/options/" + name;
    }

    //Only to add options by tech team
    @PostMapping("/options")
    public String addOptions() {
        return "/api/admin/options";
    }
}
