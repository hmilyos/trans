package com.imooc.example.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/24.
 */
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
