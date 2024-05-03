package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.Cart;
import com.coskun.jwttoken.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart,Long> {

    Cart findByUser_id(long id);
}
