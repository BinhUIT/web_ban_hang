package com.example.webbanghang.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.request.AddToCartRequest;
import com.example.webbanghang.model.request.LinkAccountRequest;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.request.UpdateCartRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.service.OAuth2Service;
import com.example.webbanghang.service.UserService;

@RestController
public class UserController {
    private final UserService userService; 
    private final OAuth2Service oAuth2Service;
    public UserController(UserService userService, OAuth2Service oAuth2Service) {
        this.userService= userService;
        this.oAuth2Service= oAuth2Service;
    }
    @GetMapping("/unsecure/test") 
    public String test() {
        return "Hello";
    }
    @GetMapping("/test/token") 
    public String testWithToken() {
        return "Success";
    }
    @GetMapping("/admin/test") 
    public String testAdmin() {
        return "Admin";
    } 
    @PostMapping("/unsecure/login") 
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        if(response==null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/unsecure/login_google") 
    public ResponseEntity<LoginResponse> loginViaGoogle(@RequestBody Map<String,String> tokenJSON) {
        String token = tokenJSON.get("token");
        Map<String,Object> result = oAuth2Service.verifyGGTokenAndGenerateToken(token);
        return getLoginResponseByResult(result);
    }
    @PutMapping("/unsecure/link_account") 
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
    @PostMapping("/user/add_to_cart") 
    public CartItem addItemToCart(@RequestBody AddToCartRequest request, Authentication auth) {
        String email = auth.getName();
        try {
            return userService.addToCart(request, email);
        } catch (Exception e) {
            String message = e.getMessage();
            if(message.equals("404")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found");
            }
            if(message.equals("400")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Bad request");
            }
        }
        return null;
    }
    @PutMapping("/user/update_cart") 
    public CartItem updateCartItem(@RequestBody UpdateCartRequest request, Authentication auth) {
        String email = auth.getName();
        try {
            return userService.updateCart(request, email);
        }
        catch (Exception e) {
            String message = e.getMessage();
            if(message.equals("404")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found");
        
            }
            if(message.equals("401")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No permission");
            }
        }
        return null;
    }
    @PutMapping("/user/update_many_cart_item") 
    public List<CartItem> updateManyCartItem(@RequestBody List<UpdateCartRequest> requests, Authentication auth) {
        String email = auth.getName();
        try {
            return userService.updateAllCartItem(requests, email);
        }
        catch (Exception e) {
            String message = e.getMessage();
            if(message.equals("404")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found");
        
            }
            if(message.equals("401")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No permission");
            }
        }
        return null;
    }
    @DeleteMapping("/user/delete_cart_item/{id}") 
    public String deleteCartItem(@PathVariable int id, Authentication auth) {
        String email = auth.getName();
        try {
            userService.deleteCartItem(id, email); 
            return "Success";
        } catch (Exception e) {
            String message = e.getMessage();
            if(message.equals("404")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        
            }
            if(message.equals("401")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No permission");
            }
        }
        return "Fail";
    }
    @DeleteMapping("/user/clear_cart")
    public String clearCart(Authentication auth) {
        String email = auth.getName();
        try {
            userService.clearCart(email);
            return "Success";
        } catch (Exception e) {
            String message = e.getMessage();
            if(message.equals("401")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No permission");
        
            }
        }
        return "Fail";
    }
    @GetMapping("/user/cart") 
    public Cart getUserCart(Authentication auth) {
        String email= auth.getName();
        return userService.getUserCart(email);
    }

}
