package com.coskun.jwttoken.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private double total;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;


    @OneToMany(
            mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,orphanRemoval = true
    )
    @ToString.Exclude
    private List<OrderItem> orderItems;
}
