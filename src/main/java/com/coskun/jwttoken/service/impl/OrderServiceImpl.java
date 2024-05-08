package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.*;
import com.coskun.jwttoken.exception.CartIsEmptyException;
import com.coskun.jwttoken.exception.ResourceNotFoundException;
import com.coskun.jwttoken.payload.OrderDto;
import com.coskun.jwttoken.payload.OrderItemDto;
import com.coskun.jwttoken.repository.*;
import com.coskun.jwttoken.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;
    private final OrderRepository orderRepository;
    private  RestaurantRepository restaurantRepository;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository, CartRepository cartRepository,RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public OrderDto createOrder(long userId) {

        User user = userRepository.findById((int)userId).orElseThrow(
                () -> new ResourceNotFoundException("User","id",userId)
        );

        Cart cart = cartRepository.findByUser_id(userId);
        List<CartItem> cartItems = cartItemRepository.findByCart_id(cart.getId());
        if(cartItems.isEmpty()){ // cart boşsa exception fırlat
            throw new CartIsEmptyException(HttpStatus.BAD_REQUEST,"Cart is empty");
        }
        Order order = new Order();
        double total=0;
        long currentRestaurantId = cartItems.get(0).getProduct().getCategory().getRestaurant().getId();
        Restaurant restaurant = restaurantRepository.findById(currentRestaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant","id",currentRestaurantId)
        );
        order.setRestaurant(restaurant);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cartItems) {
            total += ci.getPrice();
            OrderItem orderItem = mapToOrderItem(ci);
            orderItem.setOrder(order);
            System.out.println("gg");
            orderItems.add(orderItem);
        }
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(user);
        order.setTotal(total);
        order.setDate(new Date());
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);


        return mapToOrderDto(savedOrder);
    }
    // Get the customers orders
    @Override
    public List<OrderDto> getOrders(long userId) {

        User user = userRepository.findById((int)userId).orElseThrow(
                () -> new ResourceNotFoundException("User","id",userId)
        );
        List<Order> orders = orderRepository.findByCustomer_id(user.getId());
        return orders.stream().map(this::mapToOrderDto).collect(Collectors.toList());
    }

    @Override
    // Get the restaurant orderrs    // Here is the user id is the id of the restaurant admin
    public List<OrderDto> getAllOrders(long userId) {
        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant","id",userId)
        );

        List<Order> orders = orderRepository.findByRestaurant_id(restaurant.getId());
        return orders.stream().map(this::mapToOrderDto).collect(Collectors.toList());


    }




    private OrderItem mapToOrderItem(CartItem cartItem) {

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getPrice());

        return orderItem;

    }
    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());

        return orderItemDto;

    }
    private OrderDto mapToOrderDto(Order order) {

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setDate(order.getDate());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setTotal(order.getTotal());

        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(this::mapToOrderItemDto).collect(Collectors.toList());

        orderDto.setOrderItems(orderItemDtos);

        return orderDto;
    }
}
