package com.imooc.example.axon.customer.event;


/**
 * Created by mavlarn on 2018/5/22.
 */
public class CustomerChargedEvent {

    private String customerId;

    private Double amount;

    public CustomerChargedEvent() {}

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
