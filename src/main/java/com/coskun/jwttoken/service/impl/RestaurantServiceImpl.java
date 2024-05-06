package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.Restaurant;
import com.coskun.jwttoken.entity.Role;
import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.exception.ResourceNotFoundException;
import com.coskun.jwttoken.payload.RestaurantDto;
import com.coskun.jwttoken.repository.RestaurantRepository;
import com.coskun.jwttoken.repository.UserRepository;
import com.coskun.jwttoken.service.RestaurantService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RestaurantDto saveRestaurant(RestaurantDto restaurantDto) {

        Restaurant restaurant = mapToEntity(restaurantDto);

        //Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        User user = User.builder()
                .firstName(restaurantDto.getName())
                .lastName(restaurantDto.getName())
                .email(restaurantDto.getName()+"@restaurant.com")
                .password(passwordEncoder.encode("mmogluk57"))
                .role(Role.ADMIN)
                .build();
            user.setRestaurant(restaurant);
            userRepository.save(user);
            restaurant.setUser(user);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

            return mapToDTO(savedRestaurant);
    }

    @Override
    public RestaurantDto getRestaurant(long id) {

        Restaurant restaurant = userRepository.findById((int)id).get().getRestaurant();
      /*   Restaurant restaurant = restaurantRepository.findByUser_id(id).orElseThrow(
                () -> new RuntimeException("Resource not found")); */

        return mapToDTO(restaurant);
    }

    @Override
    public void deleteRestaurant(long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant", "id", restaurantId));

        restaurantRepository.delete(restaurant);
    }

    private RestaurantDto mapToDTO(Restaurant restaurant){

        RestaurantDto restaurantdto = new RestaurantDto();
        restaurantdto.setId(restaurant.getId());
        restaurantdto.setName(restaurant.getName());

        return restaurantdto;

    }

    private Restaurant mapToEntity(RestaurantDto restaurantDto){

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantDto.getId());
        restaurant.setName(restaurantDto.getName());


        return restaurant;
    }
}
