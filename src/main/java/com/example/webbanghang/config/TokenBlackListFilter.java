package com.example.webbanghang.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.webbanghang.model.entity.Token;
import com.example.webbanghang.repository.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenBlackListFilter extends OncePerRequestFilter {
    private final TokenRepository tokenRepo;
    public TokenBlackListFilter(TokenRepository tokenRepo) {
        this.tokenRepo= tokenRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            Token tk = tokenRepo.findByToken(token);
            if(tk!=null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"Token is blacklisted\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
