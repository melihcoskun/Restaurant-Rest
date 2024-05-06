package com.coskun.jwttoken.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "products"
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;

    @OneToMany(
            mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    @ToString.Exclude
    private Set<CartItem> cartItems;

}
