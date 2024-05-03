package com.coskun.jwttoken.controller;

import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.RestaurantDto;
import com.coskun.jwttoken.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {


    private RestaurantService restaurantService;


    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService=restaurantService;
    }


    @GetMapping("restaurants/myRestaurant")
    public ResponseEntity<RestaurantDto> getMapping(Authentication authentication) {


        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }


    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {

        return new ResponseEntity<RestaurantDto>(restaurantService.saveRestaurant(restaurantDto), HttpStatus.CREATED);
    }




}
