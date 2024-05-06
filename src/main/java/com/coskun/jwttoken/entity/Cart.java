package com.coskun.jwttoken.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "carts")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double totalPrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

     @OneToMany(
            mappedBy = "cart", fetch = FetchType.LAZY,
             cascade = CascadeType.ALL,orphanRemoval = true
    )
     @ToString.Exclude
     private List<CartItem> cartItems = new ArrayList<>();

     public void addCartItem(CartItem cartItem) {
         cartItems.add(cartItem);
     }



}
