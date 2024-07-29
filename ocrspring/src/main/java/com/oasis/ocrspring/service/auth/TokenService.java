package com.oasis.ocrspring.service.auth;

import com.oasis.ocrspring.model.RefreshToken;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RefreshtokenRepsitory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.oasis.ocrspring.service.auth.TokenGenerator.generateRandomToken;

@Service
public class TokenService {

    @Value("${jwt.access-secret}")
    private String accessSecret;

    @Value("${jwt.refresh-time}")
    private String refreshTime;

    @Autowired
    RefreshtokenRepsitory refreshTokenRepository;

    private long parseExpirationTime(String expiration) {
        if (expiration.endsWith("h")) {
            int hours = Integer.parseInt(expiration.replace("h", ""));
            return TimeUnit.HOURS.toMillis(hours);
        } else if (expiration.endsWith("m")) {
            int minutes = Integer.parseInt(expiration.replace("m", ""));
            return TimeUnit.MINUTES.toMillis(minutes);
        } else if (expiration.endsWith("s")) {
            int seconds = Integer.parseInt(expiration.replace("s", ""));
            return TimeUnit.SECONDS.toMillis(seconds);
        } else {
            return Long.parseLong(expiration); // Assuming it's in milliseconds
        }
    }
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes());
    }
    public String generateAccessToken(User user){
        System.out.println(parseExpirationTime(refreshTime));
        Map<String, Object> claims=new HashMap<>();
        claims.put("sub",user.getEmail());
        claims.put("role",user.getRole());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + parseExpirationTime(refreshTime)))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    public RefreshToken generateRefreshToken(User user,String ipaddress){
        RefreshToken refreshToken =new RefreshToken();
        refreshToken.setUser(user.getId());
        refreshToken.setToken(generateRandomToken(256));
        refreshToken.setExpiresAt((OffsetDateTime.now(ZoneOffset.UTC).plusDays(1).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
        refreshToken.setcreatedByIP(ipaddress);
        return refreshTokenRepository.save(refreshToken);
    }
    public void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refrshToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public String getTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("refreshToken".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }





}
