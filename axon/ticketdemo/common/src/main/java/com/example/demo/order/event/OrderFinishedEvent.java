package com.example.demo.order.event;


public class OrderFinishedEvent {

    private String orderId;

    public OrderFinishedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
