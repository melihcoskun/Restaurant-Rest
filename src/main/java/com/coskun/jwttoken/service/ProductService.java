package com.coskun.jwttoken.service;

import com.coskun.jwttoken.payload.ProductDto;

import java.util.List;

public interface ProductService {


    ProductDto createProduct(long userId, long categoryId, ProductDto productDto);

    List<ProductDto> getProductsByCategoryId(long categoryId, long restaurantId);

}
