package com.anshulp.springsecurity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10, message = "Quantity must be at most 10")
    private int quantity;

    @Column(nullable = false)
    @Min(value = 100, message = "Price must be at least 100")
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
