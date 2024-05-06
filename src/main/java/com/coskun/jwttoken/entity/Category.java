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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;

    @OneToMany(
            mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE, orphanRemoval = true
    )
    @ToString.Exclude
    private Set<Product> products;


}
