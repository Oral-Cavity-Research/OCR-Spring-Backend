package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.*;
import com.oasis.ocrspring.model.*;
import com.oasis.ocrspring.service.*;
import com.oasis.ocrspring.service.ResponseMessages.ErrorMessage;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    HospitalService hospitalService;
    @Autowired
    OptionService optionService;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    //get all requests
    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequests(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationToken.authenticateRequest(request, response);
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
    public ResponseEntity<?> getRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        authenticationToken.authenticateRequest(request, response);
        if (!tokenService.checkPermissions(request, List.of("100"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<Request> requestOptional = requestService.getRequestById(id);
            if (requestOptional.isPresent()) {
                return ResponseEntity.ok(new RequestDetailsDto(requestOptional.get()));
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("Request not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }


    //reject a request
    @PostMapping("/requests/{id}")
    public ResponseEntity<?> rejectRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody ReqestDeleteReasonDto reason) throws IOException {
        authenticationToken.authenticateRequest(request, response);
        if (!tokenService.checkPermissions(request, List.of("100"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            boolean isDeleted = requestService.rejectRequest(id, reason.getReason());
            if (isDeleted) {
                return ResponseEntity.ok().body(new ErrorMessage("Request has been deleted!"));
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("Request not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage(e.toString()));
        } catch (ErrorMessage e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody ReqToUserDto userDto) throws IOException {
        authenticationToken.authenticateRequest(request, response);
        if (!tokenService.checkPermissions(request, List.of("100"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<Request> requestOptional = requestService.getRequestById(id);
            if (requestOptional.isPresent()) {
                Request req = requestOptional.get();
                if (userService.isRegNoInUse(req.getRegNo())) {
                    return ResponseEntity.status(401).body(new ErrorMessage("Reg No already in use"));
                }

                if (userService.isEmailInUse(req.getEmail())) {
                    return ResponseEntity.status(401).body(new ErrorMessage("Email address already in use"));
                }

                User newUser = new User(
                        userDto.getUsername() != null ? userDto.getUsername() : req.getUserName(),
                        req.getEmail(),
                        req.getRegNo(),
                        userDto.getRole(),
                        req.getHospital(),
                        userDto.getDesignation() != null ? userDto.getDesignation() : "",
                        userDto.getContact_no() != null ? userDto.getContact_no() : "",
                        true
                );
                requestService.acceptRequest(id, newUser, userDto.getReason());
                userService.sendAcceptanceEmail(req.getEmail(), userDto.getReason(), req.getUserName());
                if(newUser.getUpdatedAt()==null){
                    return ResponseEntity.ok(new UserResDto(newUser.getUsername(), newUser.getEmail(), newUser.getRegNo(), newUser.getHospital(), newUser.getDesignation(), newUser.getContactNo(),newUser.isAvailable(), newUser.getRole() ,newUser.getId().toString(), newUser.getCreatedAt().toString(),  "User created successfully"));
                }
                return ResponseEntity.ok(new UserResDto(newUser.getUsername(), newUser.getEmail(), newUser.getRegNo(), newUser.getHospital(), newUser.getDesignation(), newUser.getContactNo(),newUser.isAvailable(), newUser.getRole() ,newUser.getId().toString(), newUser.getCreatedAt().toString(), newUser.getUpdatedAt().toString(), "User created successfully"));
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("Request not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        } catch (ErrorMessage e) {
            return ResponseEntity.status(500).body(e);
        }

    }



    //approve a request


    //get users by thier roles
    //only for read or write access permission
    @GetMapping("/users/role/{role}")
    public ResponseEntity<?> getUsersByRole(HttpServletRequest request, HttpServletResponse response, @PathVariable String role) throws IOException {
        authenticationToken.authenticateRequest(request, response);
        if (!tokenService.checkPermissions(request, List.of("106", "107"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<List<User>> users;
            if ("All".equals(role)) {
                users = userService.allUserDetails();
                if(users.isEmpty()){
                    return ResponseEntity.status(404).body(new ErrorMessage("No users found"));
                }
            } else {
                users = userService.getUsersByRole(role);
                if(users.isEmpty()){
                    return ResponseEntity.status(404).body(new ErrorMessage("No users found"));
                }
            }

            List<UserResDto> userResDtos = users.get().stream()
                    .map(user -> new UserResDto(
                            user.getUsername(),
                            user.getEmail(),
                            user.getRegNo(),
                            user.getHospital(),
                            user.getDesignation(),
                            user.getContactNo(),
                            user.isAvailable(),
                            user.getRole(),
                            user.getId().toString(),
                            user.getCreatedAt().toString(),
                            user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null,
                            null))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userResDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //get all user roles

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("106", "107", "100", "109"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            List<Role> roles = roleService.allRoleDetails();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //get one user role
    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRoleById(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("109"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<Role> role = roleService.getRoleById(id);
            if (role.isPresent()) {
                return ResponseEntity.ok(role.get());
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("Role not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //add a new role//////////////////////////////
    @PostMapping("/roles")
    public ResponseEntity<?> addRole(HttpServletRequest request, HttpServletResponse response, @RequestBody RoleReqDto role) throws IOException{
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("109"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            boolean isRoleAdded = roleService.addRole(role);
            if (isRoleAdded) {
                return ResponseEntity.ok(new ErrorMessage("New role added successfully"));
            } else {

                return ResponseEntity.status(401).body(new ErrorMessage("Role already exists"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //update user permission///////////////////////////
    @PostMapping("/roles/{id}")
    public ResponseEntity<?> updateRole(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody Role roleDetails) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("109"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<Role> role = roleService.getRoleById(id);
            if (role.isPresent()) {
                roleService.updateRole(id, roleDetails);
                return ResponseEntity.ok(new ErrorMessage("Role updated successfully"));
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("Role not found"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ErrorMessage("Role not found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //get a specific user
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("106", "107"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(new UserResDto(user.get()));
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //update a user
    @PostMapping("/update/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody User userDetails) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("107"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                userService.updateUser(id, userDetails);
                User updatedUser = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
                UserResDto userResDto = new UserResDto(updatedUser);
                userResDto.setMessage("User details updated successfully");
                return ResponseEntity.ok(userResDto);
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("User not found"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ErrorMessage("User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }

    //delete a user
    @PostMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("107"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            boolean isDeleted = userService.deleteUser(id);
            if (isDeleted) {
                return ResponseEntity.ok(new ErrorMessage("User deleted successfully"));
            } else {
                return ResponseEntity.status(404).body(new ErrorMessage("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }
    //add hospital//////////////////////////////////////////
    @PostMapping("/hospital")
    public ResponseEntity<?> addHospital(HttpServletRequest request, HttpServletResponse response, @RequestBody HospitalDto hospitalDetails) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("101"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            boolean isAdded = hospitalService.addHospital(hospitalDetails);
            if (isAdded) {
                return ResponseEntity.ok(new ErrorMessage("Hospital is added successfully!"));
            } else {
                return ResponseEntity.status(401).body(new ErrorMessage("Hospital is already added"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorMessage("Internal Server Error!"));
        }
    }
    //update hospital details
    @PostMapping("/hospitals/update/{id}")
    public ResponseEntity<?> updateHospital(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody HospitalDto hospitalDetails) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("101"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            Optional<Hospital> hospital = hospitalService.getHospitalById(id);
            if (hospital.isPresent()) {
                hospitalService.updateHospital(id, hospitalDetails);
                return ResponseEntity.ok(new MessageDto("Hospital details updated successfully!"));
            } else {
                return ResponseEntity.status(404).body(new MessageDto("Hospital Not Found"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new MessageDto("Hospital Not Found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageDto("Internal Server Error!"));
        }
    }

    //delete hospital
    @PostMapping("/hospitals/delete/{id}")
    public ResponseEntity<?> deleteHospital(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("101"))) {
            return ResponseEntity.status(401).body(new ErrorMessage("Unauthorized access"));
        }

        try {
            boolean isDeleted = hospitalService.deleteHospital(id);
            if (isDeleted) {
                return ResponseEntity.ok(new MessageDto("Hospital deleted successfully"));
            } else {
                return ResponseEntity.status(404).body(new MessageDto("Hospital not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageDto("Internal Server Error!"));
        }
    }

    //get all hospitals
    @GetMapping("/hospitals/{id}")
    public ResponseEntity<?> getHospitalDetails(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
        // Authenticate request
        authenticationToken.authenticateRequest(request, response);

        // Check permissions
        if (!tokenService.checkPermissions(request, List.of("101"))) {
            return ResponseEntity.status(401).body(new MessageDto("Unauthorized access"));
        }

        try {
            Optional<Hospital> hospital = hospitalService.getHospitalById(id);
            if (hospital.isPresent()) {
                return ResponseEntity.ok(hospital.get());
            } else {
                return ResponseEntity.status(404).body(new MessageDto("Hospital not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageDto("Internal Server Error!"));
        }
    }

    //get options (labels and values)

    @GetMapping("option/{name}")
    public ResponseEntity<?> getOptionByName(HttpServletRequest request,HttpServletResponse response,@PathVariable String name) throws IOException {
        authenticationToken.authenticateRequest(request, response);

        try {
            Option option = optionService.findByName(name);
            if (option == null) {
                return ResponseEntity.status(404).body(new MessageDto(name + " not found"));
            } else {
                return ResponseEntity.ok(new OptionsDto(option));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageDto("Internal Server Error!"));
        }
    }

    //Only to add options by tech team
    @PostMapping("/option")
    public ResponseEntity<?> addOption(@RequestBody OptionsDto optionsDto) {

        try {
            Option existingOption = optionService.findByName(optionsDto.getName());
            if (existingOption != null) {
                return ResponseEntity.status(401).body(new MessageDto("Option already exists"));
            } else {
                try {
                    optionService.saveOption(new Option(optionsDto.getName(), optionsDto.getOptions()));
                    return ResponseEntity.ok(new MessageDto("Option is saved"));
                } catch (Exception e) {
                    return ResponseEntity.status(500).body(new MessageDto("Internal Server Error!"));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageDto("Internal Server Error!"));
        }
    }
}
