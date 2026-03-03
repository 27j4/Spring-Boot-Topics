package com.anshulp.pubsubmodel.listeners;

import com.anshulp.pubsubmodel.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationListener {

    @EventListener
    @Async
    @Order(1)
    public void sendEmailNotification(OrderCreatedEvent orderCreatedEvent) throws InterruptedException {
        System.out.println("Sending email notification for Order ID: " + orderCreatedEvent.getOrderId() +
                ", Customer ID: " + orderCreatedEvent.getCustomerId() +
                ", Amount: $" + orderCreatedEvent.getAmount());

        Thread.sleep(1000);

        System.out.println("Email notification sent for Order ID: " + orderCreatedEvent.getOrderId());
    }
}
