package com.coskun.jwttoken.controller;

import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.RestaurantDto;
import com.coskun.jwttoken.service.RestaurantService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {


    private RestaurantService restaurantService;


    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService=restaurantService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurants/my-restaurant")
    public ResponseEntity<RestaurantDto> getMapping(Authentication authentication) {


        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {

        return new ResponseEntity<RestaurantDto>(restaurantService.saveRestaurant(restaurantDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable long restaurantId) {


        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok("Restaurant deleted succesfully.");
    }




}
