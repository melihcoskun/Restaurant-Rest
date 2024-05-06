package com.coskun.jwttoken.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private List<CardDto> cardDtoList;
    private double total;

}