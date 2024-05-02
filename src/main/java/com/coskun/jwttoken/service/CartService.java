package com.coskun.jwttoken.service;

import com.coskun.jwttoken.entity.Cart;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.payload.ProductDto;

public interface CartService {

    Cart addProductToCart(long userId, CardDto cadtDto);

}
