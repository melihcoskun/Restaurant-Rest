package com.coskun.jwttoken.service;

import com.coskun.jwttoken.entity.Cart;
import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.payload.CardResponse;
import com.coskun.jwttoken.payload.ProductDto;

import java.util.List;

public interface CartService {

    CardDto addProductToCart(long userId, CardDto cardDto);

    CardResponse getItemsInCard(long userId);

    void removeItemFromCard(long userId, long itemId);

    CardDto editItemInCard(long userId, long itemId, CardDto cardDto);
}
