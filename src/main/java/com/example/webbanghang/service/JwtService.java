package com.example.webbanghang.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.RefreshToken;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.repository.RefreshTokenRepository;
import com.example.webbanghang.repository.UserRepository;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    public static final String secret = Dotenv.load().get("SECRET_KEY");
    private final UserRepository userRepo; 
    private final RefreshTokenRepository refreshTokenRepo;
    public JwtService(UserRepository userRepo, RefreshTokenRepository refreshTokenRepo) {
        this.userRepo= userRepo;
        this.refreshTokenRepo = refreshTokenRepo;
    } 
     private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }
    public String generateRefreshToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, email);
    }
    private String createToken(Map<String,Object> claims, String email) {
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * Constants.tokenExpireTime))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    private String createRefreshToken(Map<String, Object> claims, String email) {
        String refreshToken =Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * Constants.refreshTokenExpireTime))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        RefreshToken rfToken = new RefreshToken();
        rfToken.setToken(refreshToken);
        refreshTokenRepo.save(rfToken);
        return refreshToken;
    }
    public String extractEmail(String token) {
       
        return extractClaim(token,Claims::getSubject );
    }
    public Date extractExpiredDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpire(String token) {
        return extractExpiredDate(token).before(new Date());
    }
    public boolean validateToken(String token,UserDetails userDetails) {
        if(userDetails instanceof User user) {
            String emailFromToken= extractEmail(token);
            return emailFromToken.equals(user.getEmail())&&!isTokenExpire(token);
        } 
        return false;
    }
    public boolean validateRefreshToken(String token , UserDetails userDetails) {
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token);
        if(refreshToken==null) {
            return false;
        } 
        boolean isValid = validateToken(token, userDetails);
        if(!isValid) {
            refreshTokenRepo.delete(refreshToken);
        }
        return isValid;
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    } 
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public User findUserByToken(String token) {
        String email = extractEmail(token);
        return userRepo.findByEmail(email);
    }
}
