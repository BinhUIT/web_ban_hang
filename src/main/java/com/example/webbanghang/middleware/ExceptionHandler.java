package com.example.webbanghang.middleware;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionHandler {
    public static ResponseStatusException getResponseStatusException(Exception e) {
        String message = e.getMessage();
        if(message.endsWith("not found")) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
        if(message.equals("Variant exists")) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } 
        if(message.equals("Fail to upload image, please upload again")) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,message);
        }
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
