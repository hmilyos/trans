package com.hmily.transaxon.springaxonsaga.customer.event;

public class OrderPaidEvent {
    private String orderId;
    private String customerId;
    private Double amount;

    public OrderPaidEvent() {
    }

    public OrderPaidEvent(String orderId, String customerId, Double amount) {
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

    public Double getAmount() {
        return amount;
    }
}
