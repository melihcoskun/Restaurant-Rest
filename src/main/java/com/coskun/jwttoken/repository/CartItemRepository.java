package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByCart_id(long id);

    Optional<CartItem> findByCart_idAndProduct_id(long cartId, long productId);
}
