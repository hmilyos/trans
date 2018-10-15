package com.imooc.example.axon.customer.event;

/**
 * Created by mavlarn on 2018/5/27.
 */
public class OrderPaidEvent {

    private String orderId;
    private String customerId;
    private Double amount;

    public OrderPaidEvent() {}

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
