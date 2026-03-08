package com.anshulp.springsecurity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String product;
    private int quantity;
    private double price;
}
