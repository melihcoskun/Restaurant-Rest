package com.coskun.jwttoken.controller;


import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.payload.CardResponse;
import com.coskun.jwttoken.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    private CartService cartService;

    public CardController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to my card and how many?
    @PostMapping("/my-card")
    public ResponseEntity<CardDto> addProductToCard(Authentication authentication,
                                                    @RequestBody CardDto cardDto) {
        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();
        System.out.println("Line 32 ******");
        return new ResponseEntity<>(cartService.addProductToCart(id,cardDto), HttpStatus.CREATED);

    }

    @GetMapping("/my-card")
    public ResponseEntity<CardResponse> getCardItemsInCard(Authentication authentication,
                                                           @RequestBody CardDto cardDto){

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        return ResponseEntity.ok(cartService.getItemsInCard(id));
    }

    @DeleteMapping("/my-card/{itemId}")
    public ResponseEntity<String> removeItemFromCard(Authentication authentication,
                                                     @PathVariable long itemId) {

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        cartService.removeItemFromCard(id,itemId);
        return ResponseEntity.ok("Item deleted succesfully");
    }



}
