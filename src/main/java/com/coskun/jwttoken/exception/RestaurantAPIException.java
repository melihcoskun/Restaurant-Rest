package com.coskun.jwttoken.exception;

import org.springframework.http.HttpStatus;

public class RestaurantAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public RestaurantAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public RestaurantAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
