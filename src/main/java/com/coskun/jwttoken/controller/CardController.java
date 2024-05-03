package com.coskun.jwttoken.controller;


import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private CartService cartService;

    public CardController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to my card and how many?
    @PostMapping("/my-card")
    public ResponseEntity<CartItem> addProductToCard(Authentication authentication,
                                                    @RequestBody CardDto cardDto) {
        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();
        System.out.println("Line 32 ******");
        return new ResponseEntity<>(cartService.addProductToCart(id,cardDto), HttpStatus.CREATED);

    }


}
