package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.Category;
import com.coskun.jwttoken.entity.Restaurant;
import com.coskun.jwttoken.exception.ResourceNotFoundException;
import com.coskun.jwttoken.exception.RestaurantAPIException;
import com.coskun.jwttoken.payload.CategoryDto;
import com.coskun.jwttoken.repository.CategoryRepository;
import com.coskun.jwttoken.repository.RestaurantRepository;
import com.coskun.jwttoken.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private RestaurantRepository restaurantRepository;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository) {
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto,long userId) {

        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        Category category = mapToEntity(categoryDto);
        category.setRestaurant(restaurant);
        restaurant.addCategory(category);
        Category savedCategory = categoryRepository.save(category);

        return mapToDto(savedCategory);
    }

    @Override
    public void deleteCategory(long userId,long categoryId) {


        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "id", categoryId)
        );

        if(category.getRestaurant().getId()!=restaurant.getId()){
            throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,"Category doesnt block to the restaurant");

        }
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(long userId) {

        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        List<Category> categories = categoryRepository.findByRestaurantId(restaurant.getId());

        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    private CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }
    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());

        return category;
    }
}
