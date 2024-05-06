package com.coskun.jwttoken.service;

import com.coskun.jwttoken.payload.ProductDto;

import java.util.List;

public interface ProductService {


    ProductDto createProduct(long userId, long categoryId, ProductDto productDto);

    List<ProductDto> getProductsByCategoryId(long userId, long categoryId);

    void removeProduct(long userId, long categoryId, long productId);

    ProductDto updateProduct(long userId, long categoryId, long productId, ProductDto productDto);
}
