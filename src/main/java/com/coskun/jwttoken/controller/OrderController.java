package com.coskun.jwttoken.controller;

import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.OrderDto;
import com.coskun.jwttoken.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Normal User
    @PostMapping("/my-orders/place")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDto> addOrder(Authentication authentication) {

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        return new ResponseEntity<>(
                orderService.createOrder(id), HttpStatus.CREATED);

    }
    // Normal User
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDto>> getMyOrders(Authentication authentication) {

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        return ResponseEntity.ok(orderService.getOrders(id));
    }

    // For Restaurant Admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurants/my-restaurant/orders")
    public ResponseEntity<List<OrderDto>> getRestaurantsOrders(Authentication authentication) {

        Object principal = authentication.getPrincipal();
        long id= ((User)principal).getId();

        return ResponseEntity.ok(
                orderService.getAllOrders(id));

    }

}
