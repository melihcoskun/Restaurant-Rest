package com.coskun.jwttoken.payload;

import lombok.Data;

@Data
public class OrderItemDto {

    private long id;
    private long productId;
    private double price;
    private long quantity;


}
