package com.oasis.ocrspring.service.auth;

import com.oasis.ocrspring.model.RefreshToken;
import com.oasis.ocrspring.model.Role;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.RefreshtokenRepsitory;
import com.oasis.ocrspring.service.RoleService;
import com.oasis.ocrspring.service.userService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
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
    @Autowired
    private userService userservice;
    @Autowired
    private RoleService roleService;

    private boolean checkPermissions( List<String> permissions,List<String> allowed){
        return permissions.stream().anyMatch(allowed::contains);
    }
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

    public Map<String,Object> decodeAccessToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public RefreshToken generateRefreshToken(User user,String ipaddress){

        RefreshToken refreshToken =new RefreshToken();
        refreshToken.setUser(user.getId());
        refreshToken.setToken(generateRandomToken(256));
        refreshToken.setExpiresAt(LocalDateTime.parse((LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
        refreshToken.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        refreshToken.setcreatedByIP(ipaddress);
        return refreshTokenRepository.save(refreshToken);
    }
    public void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);

        // Set the expiration time as 24 hours from the current time
        Date expiryDate = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
        cookie.setMaxAge((int) (expiryDate.getTime() / 1000)); // Convert to seconds

        cookie.setPath("/");
        cookie.setSecure(false); // Set this to true if you're using HTTPS

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

    public RefreshToken getRefreshTokenByToken(String token){
        Optional<RefreshToken> refreshToken =refreshTokenRepository.findByToken(token);
        if(!refreshToken.isPresent()){
            throw new RuntimeException("Refresh Token not found");
        }
        if(refreshToken.get().getRevokedAt()!=null || refreshToken.get().isExpired() ){
            throw new RuntimeException("Refresh Token is not valid");
        }
        return refreshToken.get();
    }

    //refreshing the refreshtoken
    public Map<String,Object> refreshToken(String token,String ipaddress){
        RefreshToken refreshToken =getRefreshTokenByToken(token);

        User user =userservice.getUserById(refreshToken.getUser().toString()).orElseThrow(()->new RuntimeException("User not found"));

        Role rolePermissions= roleService.getRoleByrole(user.getRole()).orElseThrow(()->new RuntimeException("Role not found"));

        RefreshToken newRefreshToken =generateRefreshToken(user,ipaddress);
        System.out.println(newRefreshToken.getcreatedByIP());//////////////////
        refreshToken.setRevokedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        refreshToken.setReplacedByToken(newRefreshToken.getToken());

        refreshTokenRepository.save(refreshToken);
        refreshTokenRepository.save(newRefreshToken);

        String accessToken =generateAccessToken(user);

        Map<String,Object> tokens=new HashMap<>();
        tokens.put("accessToken",accessToken);
        tokens.put("refreshToken",newRefreshToken.getToken());
        tokens.put("ref",user);
        tokens.put("permissions",rolePermissions.getPermissions());

        return tokens;

    }

    public void revokeTokenbyToken (String token,String ipaddress){
        RefreshToken refreshToken =getRefreshTokenByToken(token);
        refreshToken.setRevokedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        refreshToken.setRevokedByIP(ipaddress);
        refreshTokenRepository.save(refreshToken);
    }





}
