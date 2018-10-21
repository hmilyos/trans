package com.hmily.transaxon.springaxonsaga.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderPayCommand {


    @TargetAggregateIdentifier
    private String customerId;

    private String orderId;
    private Double amount;

    public OrderPayCommand(String customerId, String orderId, Double amount) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
