package com.example.webbanghang.service.authservice;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.middleware.PassEncoder;
import com.example.webbanghang.middleware.Validator;
import com.example.webbanghang.model.entity.Role;
import com.example.webbanghang.model.entity.Token;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.request.RegisterRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.model.response.RegisterResponse;
import com.example.webbanghang.repository.RoleRepository;
import com.example.webbanghang.repository.TokenRepository;
import com.example.webbanghang.repository.UserRepository;
import com.example.webbanghang.service.JwtService;
@Service
public class AuthService {
    
    
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepo;
    private final Validator validator;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    
    public AuthService( JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepo, Validator validator, UserRepository userRepo, RoleRepository roleRepo) {
        
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepo= tokenRepo;
        this.validator = validator;
        this.userRepo= userRepo;
        this.roleRepo= roleRepo;
        
    }
    public RegisterResponse register(RegisterRequest request){
        validator.validateRegisterRequest(request);
        String hashedPassword = PassEncoder.getPassEncoder().encode(request.getPassword());
        Role role = roleRepo.findById(2).orElse(null);
        User user = new User(request, hashedPassword,role);
        user = userRepo.save(user);
        return new RegisterResponse(user);
    }
    public LoginResponse login(LoginRequest request) {
       
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if(auth.isAuthenticated()) {
            Object userPrincipal = auth.getPrincipal();
            if(userPrincipal instanceof User user) {
                return new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime*60*1000), jwtService.generateRefreshToken(user.getEmail()));
            }
            return null;
        }
        return null;
    }
    public void logout(String tk) {
        Token token = new Token();
        token.setToken(tk);
        tokenRepo.save(token);
    }

}
