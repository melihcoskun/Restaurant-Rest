package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Optional<Restaurant> findByUser_id(long id);

}
