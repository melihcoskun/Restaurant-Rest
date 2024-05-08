package com.coskun.jwttoken.controller;


import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.CategoryDto;
import com.coskun.jwttoken.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/my-restaurant/categories")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto,
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        return new ResponseEntity<>(categoryService.createCategory(categoryDto,id), HttpStatus.CREATED);

    }


    // This is for restaurant owner
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/my-restaurant/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

       return ResponseEntity.ok(categoryService.getAllCategories(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/my-restaurant/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId,
                                                 Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        categoryService.deleteCategory(id,categoryId);

        return ResponseEntity.ok("Category deleted succesfully");

    }


}
