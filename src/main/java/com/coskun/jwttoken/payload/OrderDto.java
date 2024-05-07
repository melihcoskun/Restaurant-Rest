package com.coskun.jwttoken.payload;

import com.coskun.jwttoken.entity.OrderItem;
import com.coskun.jwttoken.entity.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private long id;
    private Date date;
    private OrderStatus orderStatus;
    private double total;
    private List<OrderItemDto> orderItems;

}
