package com.coskun.jwttoken.controller;


import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.payload.CardResponse;
import com.coskun.jwttoken.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-card")
@PreAuthorize("hasRole('CUSTOMER')")
public class CardController {

    private CartService cartService;

    public CardController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to my card and how many?
    @PostMapping()
    public ResponseEntity<CardDto> addProductToCard(Authentication authentication,
                                                    @RequestBody CardDto cardDto) {
        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();
        System.out.println("Line 32 ******");
        return new ResponseEntity<>(cartService.
                addProductToCart(id,cardDto), HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<CardResponse> getCardItemsInCard(Authentication authentication,
                                                           @RequestBody CardDto cardDto){

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        return ResponseEntity.ok(cartService.getItemsInCard(id));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> removeItemFromCard(Authentication authentication,
                                                     @PathVariable long itemId) {

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        cartService.removeItemFromCard(id,itemId);
        return ResponseEntity.ok("Item deleted succesfully");
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CardDto> editItem(Authentication authentication,
                                            @PathVariable long itemId,
                                            @RequestBody CardDto cardDto) {

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();
        CardDto cardDto1 =cartService.editItemInCard(id,itemId,cardDto);
        return new ResponseEntity<>(cardDto1, HttpStatus.OK);

    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCard(Authentication authentication){

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();
        cartService.clearCard(id);

        return ResponseEntity.ok("Cart cleared succesfullly");
    }



}
