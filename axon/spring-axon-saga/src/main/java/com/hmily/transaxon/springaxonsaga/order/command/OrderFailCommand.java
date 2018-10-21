package com.hmily.transaxon.springaxonsaga.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderFailCommand {
    @TargetAggregateIdentifier
    private String orderId;

    private String reason;

    public OrderFailCommand(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}
