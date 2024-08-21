package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.dto.EmailDto;
import com.oasis.ocrspring.dto.RequestDto;
import com.oasis.ocrspring.dto.TokenRequest;
import com.oasis.ocrspring.model.RefreshToken;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RequestRepository;
import com.oasis.ocrspring.service.RefreshtokenService;
import com.oasis.ocrspring.service.ResponseMessages.ErrorResponse;
import com.oasis.ocrspring.service.RoleService;
import com.oasis.ocrspring.service.UserService;
import com.oasis.ocrspring.service.auth.AuthenticationToken;
import com.oasis.ocrspring.service.auth.TokenService;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {
    @Autowired
    private RequestRepository requestRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RefreshtokenService refreshTokenService;
    @Autowired
    private AuthenticationToken authenticationToken;

    @Value("${jwt.refresh-time}")
    private String refreshTime;

    @ApiIgnore
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> userSignup(@RequestBody RequestDto request) {
        String message = userService.signup(request);
        if (message.equals("Request is sent successfully. You will receive an Email on acceptance")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(401).body(message);
        }

    }

    @PostMapping("/verify")
    public ResponseEntity<?> userVerify(@RequestBody EmailDto emailbody, HttpServletRequest httpServletRequest, HttpServletResponse response){

        String email= emailbody.getEmail();


        Optional<User> user = userService.getUserByEmail(email);
        if (!user.isPresent()) {
            Map<String, String> messageBody = new HashMap<>();
            messageBody.put("message", "User is Not Registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageBody);
        }
        String accessToken = tokenService.generateAccessToken(user.get());
        RefreshToken refreshToken = tokenService.generateRefreshToken(user.get(), httpServletRequest.getRemoteAddr());//saving the refreshtoken to the database

        tokenService.setTokenCookie(response, refreshToken.getToken());

        Optional<Role> rolePermission = roleService.getRoleByrole(user.get().getRole());
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> details = new HashMap<>();

        details.put("user", user.get());

        details.put("message", "Successfully logged in");

        details.put("permissions", rolePermission.get().getPermissions());


        responseBody.put("others", details);
        responseBody.put("ref", user.get());
        responseBody.put("accessToken", Map.of("token", accessToken, "expiresAt", refreshTime));
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String token = tokenService.getTokenFromCookie(httpServletRequest);
        if (token == null || token.isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(false, "Token is Required"));
        }

        String ipaddress = httpServletRequest.getRemoteAddr();
        Map<String, Object> tokenBody = tokenService.refreshToken(token, ipaddress);

        String accessToken = (String) tokenBody.get("accessToken");

        String refreshToken = (String) tokenBody.get("refreshToken");

        User ref = (User) tokenBody.get("ref");

        List<String> permissions = (List<String>) tokenBody.get("permissions");


        tokenService.setTokenCookie(response, refreshToken);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("message", "Token Refreshed Successfully");
        responseBody.put("ref", ref);
        responseBody.put("permissions", permissions);
        responseBody.put("accessToken", Map.of("token", accessToken, "expiry", refreshTime));
        return ResponseEntity.ok(responseBody);

    }

    @PostMapping("/revokeToken")
    public ResponseEntity<?> revokeToken(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) TokenRequest tokenRequest) throws IOException {
        authenticationToken.authenticateRequest(request, response);

        String token = tokenService.getTokenFromCookie(request);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(false, "Token is Required"));
        }

        AuthenticationToken.TokenOwner ownsToken = (AuthenticationToken.TokenOwner) request.getAttribute("ownsToken");
        if (!ownsToken.ownsToken(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(false, "You are not authorized to revoke this token"));
        }

        String ipAddress = request.getRemoteAddr();
        try {
            tokenService.revokeTokenbyToken(token, ipAddress);
            return ResponseEntity.ok(new ErrorResponse(true, "Token Revoked Successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(false, e.getMessage()));
        }
    }
}
