package com.imooc.example.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/24.
 */
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
