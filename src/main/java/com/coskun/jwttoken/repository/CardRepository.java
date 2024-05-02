package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository  extends JpaRepository<Cart,Long> {
}
