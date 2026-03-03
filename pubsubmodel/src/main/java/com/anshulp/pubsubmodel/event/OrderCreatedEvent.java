package com.anshulp.pubsubmodel.event;

public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private double amount;

    public OrderCreatedEvent(String orderId, String customerId, double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }
}
