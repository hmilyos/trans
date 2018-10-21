package com.hmily.transaxon.springaxonsaga.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderFinishCommand {
    @TargetAggregateIdentifier
    private String orderId;

    public OrderFinishCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
