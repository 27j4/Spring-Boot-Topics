package com.anshulp.pubsubmodel.listeners;

import com.anshulp.pubsubmodel.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogOrderDetailsListener {

    // @Order(2) // Optional: Set the order of execution if multiple listeners are present

    // In this scenario, @Order does not guarantee the execution order of listeners
    // when @Async is used, as they will run in separate threads.
    @EventListener
    @Async // if we want to execute this listener asynchronously
    @Order(2)
    public void logOrderCreated(OrderCreatedEvent event) throws InterruptedException {
        System.out.println("Order Placed For Order Id: " + event.getOrderId());
        Thread.sleep(1000);
        System.out.println("Order Details: Order ID: " + event.getOrderId() +
                ", Customer ID: " + event.getCustomerId() +
                ", Amount: $" + event.getAmount());
    }
}