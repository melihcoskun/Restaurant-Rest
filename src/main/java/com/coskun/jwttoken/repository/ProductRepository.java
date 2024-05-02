package com.coskun.jwttoken.repository;

import com.coskun.jwttoken.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
