package com.coskun.jwttoken.service;


import com.coskun.jwttoken.payload.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(long userId);

    List<OrderDto> getOrders(long userId);
}
