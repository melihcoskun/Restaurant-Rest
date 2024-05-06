package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.*;
import com.coskun.jwttoken.exception.ResourceNotFoundException;
import com.coskun.jwttoken.exception.RestaurantAPIException;
import com.coskun.jwttoken.payload.CardDto;
import com.coskun.jwttoken.payload.CardResponse;
import com.coskun.jwttoken.repository.CartItemRepository;
import com.coskun.jwttoken.repository.CartRepository;
import com.coskun.jwttoken.repository.ProductRepository;
import com.coskun.jwttoken.repository.UserRepository;
import com.coskun.jwttoken.service.CartItemService;
import com.coskun.jwttoken.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CardDto addProductToCart(long userId, CardDto cardDto) {

        Product product = productRepository.findById(cardDto.getProductId()).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        Cart cart = cartRepository.findByUser_id(userId);

        List<CartItem> cartItems = cartItemRepository.findByCart_id(cart.getId());

        if(cartItems.isEmpty()) { // cart Boşsa ekle

            CartItem cartItem = cartItemService.createCartItem(cardDto);
            cart.setTotalPrice(cart.getTotalPrice()+cartItem.getPrice());
            cartItem.setCart(cart);
            cart.addCartItem(cartItem);

            cartRepository.save(cart);
            CartItem savedCartItem = cartItemRepository.save(cartItem);


            return mapToCardDto(savedCartItem);


        } else {

            long currentRestaurantId = cartItems.get(0).getProduct().getCategory().getRestaurant().getId();
            long wantedRestaurantId = product.getCategory().getRestaurant().getId();

            if(currentRestaurantId == wantedRestaurantId){  // aynı restaurantsa
                Optional<CartItem> cartItemExist = cartItemRepository.findByCart_idAndProduct_id(cart.getId(), product.getId());
                if(cartItemExist.isEmpty()) { // Burda o product id var mı diye bakıcaz şimdi.
                    // burda ekle

                    CartItem cartItem = cartItemService.createCartItem(cardDto);
                    cart.setTotalPrice(cart.getTotalPrice()+cartItem.getPrice());
                    cartRepository.save(cart);
                    cartItem.setCart(cart);
                    return mapToCardDto(cartItemRepository.save(cartItem));

                } else {
                    // Burda ekleyemezsin.
                    throw new RestaurantAPIException(HttpStatus.NOT_FOUND,
                            "The product you are trying to add to card, is already exist in the card.");
                }


            } else {

                throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,
                        "You are trying to add product from different restaurant");
            }

        }
    }

    @Override
    public CardResponse getItemsInCard(long userId) {

        Cart cart = cartRepository.findByUser_id(userId);

        //List<CartItem> cartItems = cartItemRepository.findByCart_id(cart.getId());
        List<CartItem> cartItems = cart.getCartItems();

        CardResponse cardResponse = new CardResponse();


        List<CardDto> content =  cartItems.stream().map(this::mapToCardDto).collect(Collectors.toList());
        cardResponse.setCardDtoList(content);
        double sum = content.stream().mapToDouble(CardDto::getTotalPrice).sum();
        cardResponse.setTotal(sum);
        return cardResponse;
    }
    @Override
    public void removeItemFromCard(long userId, long itemId){

        Cart cart = cartRepository.findByUser_id(userId);
        CartItem cartItem = cartItemRepository.findByCart_idAndProduct_id(cart.getId(), itemId).orElseThrow(
                () ->     new ResourceNotFoundException("item" , "id" ,itemId)
        );
        cartItemRepository.delete(cartItem);

    }


    /*
    @Override
    public CartItem addProductToCart(long userId, CardDto cardDto) {

        User user = userRepository.findById((int) userId).orElseThrow(
                () -> new RuntimeException("Resource not found"));
        Product product = productRepository.findById(cardDto.getProductId()).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        Cart cart = cartRepository.findByUser_id(userId);
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



    } */

    private CardDto mapToCardDto(CartItem cartItem) {
        CardDto cardDto = new CardDto();
        cardDto.setQuantity(cartItem.getQuantity());
        cardDto.setProductId(cartItem.getProduct().getId());
        cardDto.setTotalPrice(cartItem.getPrice());
        cardDto.setId(cartItem.getId());
        return cardDto;
    }
}
