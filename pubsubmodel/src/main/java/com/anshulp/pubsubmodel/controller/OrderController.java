package com.anshulp.pubsubmodel.controller;

import com.anshulp.pubsubmodel.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{orderId}")
    public String createOrder(@PathVariable String orderId) {
        String customerId = "CUST" + orderId;
        double amount = 100.0 * Integer.parseInt(orderId);
        return orderService.createOrder(orderId, customerId, amount);
    }

}
