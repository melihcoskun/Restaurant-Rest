package com.coskun.jwttoken.payload;

import lombok.Data;

@Data
public class CardDto {

    private long id;
    private long productId;
    private long quantity;
    private double totalPrice;
}
