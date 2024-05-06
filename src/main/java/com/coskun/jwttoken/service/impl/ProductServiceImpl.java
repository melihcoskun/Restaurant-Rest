package com.coskun.jwttoken.service.impl;

import com.coskun.jwttoken.entity.Category;
import com.coskun.jwttoken.entity.Product;
import com.coskun.jwttoken.entity.Restaurant;
import com.coskun.jwttoken.exception.ResourceNotFoundException;
import com.coskun.jwttoken.exception.RestaurantAPIException;
import com.coskun.jwttoken.payload.ProductDto;
import com.coskun.jwttoken.repository.CartItemRepository;
import com.coskun.jwttoken.repository.CategoryRepository;
import com.coskun.jwttoken.repository.ProductRepository;
import com.coskun.jwttoken.repository.RestaurantRepository;
import com.coskun.jwttoken.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;
    private RestaurantRepository restaurantRepository;
    private CategoryRepository categoryRepository;
    private CartItemRepository cartItemRepository;

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
    public List<ProductDto> getProductsByCategoryId(long userId, long categoryId) {

        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id" , userId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id" , categoryId));


        if(category.getRestaurant().getId() != restaurant.getId()) {
            throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,
                    "Category and restaurant id dosnt match");
        }

        List<Product> products = productRepository.findByCategory_id(categoryId);
        return products.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public void removeProduct(long userId, long categoryId, long productId) {


        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id" , userId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id" , categoryId));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId));
        if(category.getRestaurant().getId() != restaurant.getId()) {
            throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,
                    "Category and restaurant id dosnt match");
        }
        if(product.getCategory().getId() != category.getId()) {
            throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,
                    "Category and Product id dosnt match");
        }

        productRepository.delete(product);


    }

    @Override
    public ProductDto updateProduct(long userId, long categoryId, long productId,ProductDto productDto) {


        Restaurant restaurant = restaurantRepository.findByUser_id(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id" , userId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id" , categoryId));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId));
        if(category.getRestaurant().getId() != restaurant.getId()) {
            throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,
                    "Category and restaurant id dosnt match");
        }
        if(product.getCategory().getId() != category.getId()) {
            throw new RestaurantAPIException(HttpStatus.BAD_REQUEST,
                    "Category and Product id dosnt match");
        }

        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        product.getCartItems().stream().forEach(
                cartItem -> cartItem.setPrice(product.getPrice()*cartItem.getQuantity())
        );
        return mapToDto(productRepository.save(product));

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
