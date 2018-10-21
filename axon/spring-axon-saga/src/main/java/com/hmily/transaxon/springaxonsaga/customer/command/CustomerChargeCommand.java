package com.hmily.transaxon.springaxonsaga.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

public class CustomerChargeCommand {

    @TargetAggregateIdentifier
    private String customerId;

    @Min(value = 1, message = "金额最小为1")
    private Double amount;

    public CustomerChargeCommand(String customerId, Double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
