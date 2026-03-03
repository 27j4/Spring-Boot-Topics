package com.anshulp.pubsubmodel.service;

import com.anshulp.pubsubmodel.event.OrderCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public String createOrder(String orderId, String customerId, double amount) {
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(orderId, customerId, amount));
        return "Order " + orderId + " created for customer " + customerId + " with amount $" + amount;
    }
}
