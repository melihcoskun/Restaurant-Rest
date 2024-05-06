package com.coskun.jwttoken.service;

import com.coskun.jwttoken.payload.RestaurantDto;

public interface RestaurantService {

    RestaurantDto saveRestaurant(RestaurantDto restaurantDto);

    RestaurantDto getRestaurant(long id);

    void deleteRestaurant(long restaurantId);
}
