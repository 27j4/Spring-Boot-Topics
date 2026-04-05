package com.anshulp.transaction.mapper;

import com.anshulp.transaction.dto.OrderResponse;
import com.anshulp.transaction.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public static OrderResponse toDto(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserName(order.getUser().getName());
        response.setProductName(order.getProductName());
        response.setAmount(order.getAmount());
        return response;
    }

    public static Order toEntity(OrderResponse response) {
        Order order = new Order();
        order.setId(response.getId());
        order.setProductName(response.getProductName());
        order.setAmount(response.getAmount());
        return order;
    }
}
