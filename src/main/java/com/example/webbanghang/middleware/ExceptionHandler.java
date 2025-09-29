package com.example.webbanghang.middleware;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.exception.InternalServerErrorException;
import com.example.webbanghang.exception.NotFoundException;
import com.example.webbanghang.exception.UnauthorizedException;

public class ExceptionHandler {
    public static ResponseStatusException getResponseStatusException(Exception e) {
        String message = e.getMessage();
        if(e instanceof NotFoundException) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND,message);
        }
        if(e instanceof UnauthorizedException) {
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED,message);
        }
        if(e instanceof BadRequestException) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        if(e instanceof InternalServerErrorException) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
        if(message.endsWith("not found")) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
        if(message.equals("Variant exists")) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } 
        if(message.equals("Fail to upload image, please upload again")) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,message);
        }
        if(message.equals("Can not delete")) {
            return new ResponseStatusException(HttpStatus.CONFLICT, message);
        }
        if(message.equals("401")) {
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED,message);
        }
        e.printStackTrace();
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
