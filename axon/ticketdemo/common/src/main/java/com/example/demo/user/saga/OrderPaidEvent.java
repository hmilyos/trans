package com.example.demo.user.saga;


public class OrderPaidEvent {

    private String orderId;
    private String customerId;
    private Double amount;

    public OrderPaidEvent(String orderId, String customerId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCustomerId() {
        return customerId;
    }
}
