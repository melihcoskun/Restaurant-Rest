package com.coskun.jwttoken.controller;


import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.payload.ProductDto;
import com.coskun.jwttoken.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/my-restaurant/categories")

public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProduct(@PathVariable long categoryId,
                                                    @RequestBody ProductDto productDto,
                                                    Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        return new ResponseEntity<>(productService.createProduct(id,categoryId,productDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable long categoryId,
                                                        Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        return ResponseEntity.ok(productService.getProductsByCategoryId(id,categoryId));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<String> removeProductFromRestaurant(@PathVariable long categoryId,
                                                        @PathVariable long productId,
                                                        Authentication authentication) {

        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();

        productService.removeProduct(id,categoryId,productId);
        return ResponseEntity.ok("Deleted Succesfully");

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long categoryId,
                                                    @PathVariable long productId,
                                                    Authentication authentication,
                                                    @RequestBody ProductDto productDto) {


        Object principal = authentication.getPrincipal();

        long id= ((User)principal).getId();
        return ResponseEntity.ok(productService.updateProduct(id,categoryId,productId,productDto));
    }


}
