package com.anshulp.pubsubmodel.listeners;

import com.anshulp.pubsubmodel.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HighValueOrderListener {


    @EventListener(condition = "#orderCreatedEvent.amount > 1000")
    public void handleHighValueOrder(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("High value order detected! Order ID: " + orderCreatedEvent.getOrderId() +
                ", Customer ID: " + orderCreatedEvent.getCustomerId() +
                ", Amount: $" + orderCreatedEvent.getAmount());
    }

}
