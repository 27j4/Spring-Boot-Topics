package com.anshulp.transaction.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponse {
    private Long id;
    private String userName;
    private String productName;
    private Double amount;
}
