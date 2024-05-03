package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.Cart;
import com.coskun.jwttoken.entity.CartItem;
import com.coskun.jwttoken.entity.Category;
import com.coskun.jwttoken.entity.Product;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.repository.CartItemRepository;
import com.coskun.jwttoken.repository.ProductRepository;
import com.coskun.jwttoken.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {


    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }


    @Override
    public CartItem createCartItem(CardDto cardDto) {

        Product product = productRepository.findById(cardDto.getProductId()).orElseThrow(() ->
                new RuntimeException("Resource not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setPrice(cardDto.getQuantity()*product.getPrice());
        cartItem.setQuantity(cardDto.getQuantity());

        return cartItem;
    }
}
