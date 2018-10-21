package com.hmily.transaxon.springaxonsaga.customer.event;

public class CustomerChargedEvent {
    private String customerId;

    private Double amount;

    public CustomerChargedEvent() {
    }

    public CustomerChargedEvent(String customerId, Double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Double getAmount() {
        return amount;
    }
}
