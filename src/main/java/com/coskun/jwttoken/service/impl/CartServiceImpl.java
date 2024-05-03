package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.*;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.repository.CartItemRepository;
import com.coskun.jwttoken.repository.CartRepository;
import com.coskun.jwttoken.repository.ProductRepository;
import com.coskun.jwttoken.repository.UserRepository;
import com.coskun.jwttoken.service.CartItemService;
import com.coskun.jwttoken.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private CartItemService cartItemService;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, CartItemService cartItemService, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItem addProductToCart(long userId, CardDto cardDto) {

        User user = userRepository.findById((int) userId).orElseThrow(
                () -> new RuntimeException("Resource not found"));
        Product product = productRepository.findById(cardDto.getProductId()).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        Optional<Cart> cart = cartRepository.findByUser_id(userId);
        if(cart.isEmpty()) { // Cart yoksa

            // Yeni bir card yarat ve usera ekle
                Cart newCart = new Cart();
                newCart.setTotalPrice(0);
                newCart.setUser(user);


                CartItem cartItem = cartItemService.createCartItem(cardDto);
                newCart.setTotalPrice(newCart.getTotalPrice()+cartItem.getPrice());
                cartRepository.save(newCart);
                cartItem.setCart(newCart);
                return cartItemRepository.save(cartItem);
            // Cart boşsa null a eşitleriz gideriz.carttan item silme işleminde unutma bunu yap ???
        } else { // Cart varsa
            // Card var ve cart itemsi boşsa gelicek buraya şimdi
            Cart existingCart = cart.get();
            List<CartItem> cartItems = cartItemRepository.findByCart_id(existingCart.getId());


                    long currentRestaurantId = cartItems.get(0).getProduct().getCategory().getRestaurant().getId();
                    long wantedRestaurantId = product.getCategory().getRestaurant().getId();

                    if(currentRestaurantId == wantedRestaurantId) { // Aynı restaurant değilse ekletmeyelim direkt bence.

                        // Burayada şeyi ekleyelim eklemeye çalıştığı ürün cartta varsa ekletmesin?
                        // Burayı daha sonra yap

                        CartItem cartItem = cartItemService.createCartItem(cardDto);
                        existingCart.setTotalPrice(existingCart.getTotalPrice()+cartItem.getPrice());
                        cartItem.setCart(existingCart);
                        return cartItemRepository.save(cartItem);



                    } else {
                        throw new RuntimeException("Not same restaurant");
                    }



        }



    }

}
