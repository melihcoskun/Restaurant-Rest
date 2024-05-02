package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.Category;
import com.coskun.jwttoken.entity.Product;
import com.coskun.jwttoken.entity.Restaurant;
import com.coskun.jwttoken.payload.ProductDto;
import com.coskun.jwttoken.repository.CategoryRepository;
import com.coskun.jwttoken.repository.ProductRepository;
import com.coskun.jwttoken.repository.RestaurantRepository;
import com.coskun.jwttoken.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;
    private RestaurantRepository restaurantRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository
            , RestaurantRepository restaurantRepository
            , CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto createProduct( long userId,long categoryId, ProductDto productDto) {

        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new RuntimeException("Resource not found"));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new RuntimeException("Resource not found"));

        if(category.getRestaurant().getId()!=restaurant.getId()) {
            throw new RuntimeException("Resource not found bad request");
        }

        Product product = mapToEntity(productDto);
        product.setCategory(category);

        Product newProduct = productRepository.save(product);

        return mapToDto(newProduct);
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(long categoryId, long restaurantId) {
        return List.of();
    }

    private Product mapToEntity(ProductDto productDto) {

        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setId(productDto.getId());

        return product;
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        productDto.setId(product.getId());
        return productDto;
    }
}
