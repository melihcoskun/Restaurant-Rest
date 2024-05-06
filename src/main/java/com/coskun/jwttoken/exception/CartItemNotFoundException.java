package com.coskun.jwttoken.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartItemNotFoundException extends RuntimeException{


    private HttpStatus status;
    private String message;

    public CartItemNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public CartItemNotFoundException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
