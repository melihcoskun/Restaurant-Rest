package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByCart_id(long id);
}
