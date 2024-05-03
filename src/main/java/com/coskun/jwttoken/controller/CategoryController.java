package com.coskun.jwttoken.controller;


import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.CategoryDto;
import com.coskun.jwttoken.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("restaurants/myRestaurant/categories")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto,
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        return new ResponseEntity<>(categoryService.createCategory(categoryDto,id), HttpStatus.CREATED);

    }

    @GetMapping("restaurants/myRestaurant/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

       return ResponseEntity.ok(categoryService.getAllCategories(id));

    }


}