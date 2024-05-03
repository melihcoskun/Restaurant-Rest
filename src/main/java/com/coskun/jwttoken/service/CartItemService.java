package com.coskun.jwttoken.service;

import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.payload.CardDto;

public interface CartItemService {


    CartItem createCartItem(CardDto cardDto);
}
