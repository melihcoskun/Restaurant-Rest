package com.coskun.jwttoken.service;

import com.coskun.jwttoken.entity.Cart;
import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.payload.ProductDto;

public interface CartService {

    CartItem addProductToCart(long userId, CardDto cardDto);

}
