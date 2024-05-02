package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    List<Category> findByRestaurantId(long restaurantId);

}
