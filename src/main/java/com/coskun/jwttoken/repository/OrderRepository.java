package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomer_id(long customerId);
}
