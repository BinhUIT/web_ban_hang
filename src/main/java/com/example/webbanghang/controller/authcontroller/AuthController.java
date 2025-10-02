package com.example.webbanghang.controller.authcontroller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.request.LinkAccountRequest;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.request.RegisterRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.model.response.RegisterResponse;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.JwtService;
import com.example.webbanghang.service.authservice.AuthService;
import com.example.webbanghang.service.authservice.OAuth2Service;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final OAuth2Service oAuth2Service;
    private final JwtService jwtService;
    public AuthController(AuthService authService, OAuth2Service oAuth2Service, JwtService jwtService) {
        this.authService= authService;
        this.oAuth2Service = oAuth2Service;
        this.jwtService= jwtService;
    } 
    @PostMapping("/login") 
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        if(response==null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/register") 
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = authService.register(request);
        Response response = new Response("Success", registerResponse, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/login_google") 
    public ResponseEntity<LoginResponse> loginViaGoogle(@RequestBody Map<String,String> tokenJSON) {
        String token = tokenJSON.get("token");
        Map<String,Object> result = oAuth2Service.verifyGGTokenAndGenerateToken(token);
        return getLoginResponseByResult(result);
    }
    @PutMapping("/link_account") 
    public ResponseEntity<LoginResponse> linkAccount(@RequestBody LinkAccountRequest request) {
        Map<String,Object> result = oAuth2Service.linkAccount(request);
        return getLoginResponseByResult(result);
        
    }
    private ResponseEntity<LoginResponse> getLoginResponseByResult(Map<String,Object> result) {
        Object result_code = result.get("Status code");
        if(result_code instanceof Integer resultCode) {
           if(resultCode!=200) {
            return new ResponseEntity<>(null, HttpStatus.valueOf(resultCode));
           }
            Object response = result.get("Data");
            if(response instanceof LoginResponse res) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/logout") 
    public String logout(@RequestParam(defaultValue="") String accessToken, @RequestParam(defaultValue="") String refreshToken) {
        jwtService.handleLogout(accessToken, refreshToken);
        return "Success";
    }

    @GetMapping("/get_new_access_token") 
    public ResponseEntity<Response> getNewAccessToken(@RequestParam(defaultValue="") String refreshToken) {
        String newAcessToken = jwtService.createNewAcessToken(refreshToken);
        Response response = new Response("Success", newAcessToken, 200);
        return new ResponseEntity(response, HttpStatus.OK);
    }
   
}
