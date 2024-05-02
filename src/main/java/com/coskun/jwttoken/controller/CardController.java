package com.coskun.jwttoken.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {


    // Add product to my card and how many?
    @PostMapping("/my-card")
    public void addProductToCard(Authentication authentication) {



    }


}
