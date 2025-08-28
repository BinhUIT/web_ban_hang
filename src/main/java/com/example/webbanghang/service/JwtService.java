package com.example.webbanghang.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    public static final String secret = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";
    private UserRepository userRepo; 
    public JwtService(UserRepository userRepo) {
        this.userRepo= userRepo;
    } 
     private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }
    private String createToken(Map<String,Object> claims, String email) {
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * Constants.tokenExpireTime))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
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
