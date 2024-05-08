package com.coskun.jwttoken.service;


import com.coskun.jwttoken.payload.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(long userId);

    List<OrderDto> getOrders(long userId);

    // Get the restaurant orderrs    // Here is the user id is the id of the restaurant admin
    List<OrderDto> getAllOrders(long userId);
}
