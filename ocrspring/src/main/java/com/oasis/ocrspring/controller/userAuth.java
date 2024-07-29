package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.EmailDto;
import com.oasis.ocrspring.model.RefreshToken;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RequestRepository;
import com.oasis.ocrspring.service.RoleService;
import com.oasis.ocrspring.service.auth.TokenService;
import com.oasis.ocrspring.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class userAuth {
    @Autowired
    private RequestRepository RequestRepo;
    @Autowired
    private userService userservice;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleService roleService;
    @Value("${jwt.refresh-time}")
    private String refreshTime;
    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @PostMapping("/signup")
    public ResponseEntity<String> userSignup(@RequestBody Request request){
        String message = userservice.signup(request);
        if(message.equals("Request is sent successfully. You will receive an Email on acceptance")){
            return ResponseEntity.ok(message);
        }else{
            return ResponseEntity.status(401).body(message);
        }

    }
    @PostMapping("/verify")
    public ResponseEntity<?> userVerify(@RequestBody EmailDto emailbody, HttpServletRequest httpServletRequest, HttpServletResponse response){
        String email= emailbody.getEmail();
      //  System.out.println(email);
        Optional<User> user =userservice.getUserByEmail(email);
        if(!user.isPresent()){
            Map<String,String> messageBody = new HashMap<>();
            messageBody.put("message","User is Not Registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageBody);
        }
        String accessToken = tokenService.generateAccessToken(user.get());
      //  System.out.println(accessToken);
        RefreshToken refreshToken =tokenService.generateRefreshToken(user.get(),httpServletRequest.getRemoteAddr());//saving the refreshtoken to the database
      //  System.out.println(refreshToken.getToken());
        tokenService.setTokenCookie(response, refreshToken.getToken());
       //System.out.println("hello");
        Optional<Role> RolePermission =roleService.getRoleByrole(user.get().getRole());
        //System.out.println(RolePermission.get().getPermissions());
        Map<String, Object>responseBody = new HashMap<>();
        Map<String, Object>details = new HashMap<>();

        details.put("user",user.get());

        details.put("message", "Successfully logged in");

        details.put("permissions",RolePermission.get().getPermissions());


        responseBody.put("others",details);
        responseBody.put("ref",user.get());
        responseBody.put("accessToken",Map.of("token",accessToken,"expiresAt",refreshTime));




        return ResponseEntity.ok(responseBody);



        // return "/api/auth/verify";
    }
//    @PostMapping("/refreshToken")
//    public ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest){
//        String token = tokenService.getTokenFromCookie(httpServletRequest);
//        if(token==null){
//
//        }
//
//    }
    @PostMapping("revokeToken")
    public String revokeToken(){
        return "/api/auth/revokeToken";
    }

}
