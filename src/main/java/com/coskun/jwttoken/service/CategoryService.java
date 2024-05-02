package com.coskun.jwttoken.service;

import com.coskun.jwttoken.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto,long userId);

    List<CategoryDto> getAllCategories(long userId);
}
