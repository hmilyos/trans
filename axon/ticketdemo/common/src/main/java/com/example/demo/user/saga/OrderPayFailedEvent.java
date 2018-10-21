package com.example.demo.user.saga;

public class OrderPayFailedEvent {

    private String orderId;

    public OrderPayFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
