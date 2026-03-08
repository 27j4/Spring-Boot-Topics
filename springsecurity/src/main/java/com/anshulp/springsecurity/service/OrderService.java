package com.anshulp.springsecurity.service;

import com.anshulp.springsecurity.dto.OrderRequest;
import com.anshulp.springsecurity.dto.OrderResponse;
import com.anshulp.springsecurity.entity.Order;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private OrderResponse maptoOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setProduct(order.getProduct());
        response.setQuantity(order.getQuantity());
        response.setPrice(order.getPrice());
        return response;
    }

    public OrderResponse createOrder(OrderRequest orderRequest, User user) {
        Order order = new Order();
        order.setProduct(orderRequest.getProductName());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setUser(user);
        orderRepository.save(order);
        return maptoOrderResponse(order);
    }
}
