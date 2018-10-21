package com.example.demo.user.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;


public class OrderPayCommand {

    @TargetAggregateIdentifier
    private String customerId;

    private String orderId;
    private Double amount;

    public OrderPayCommand(String orderId, String customerId, Double amount) {
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
