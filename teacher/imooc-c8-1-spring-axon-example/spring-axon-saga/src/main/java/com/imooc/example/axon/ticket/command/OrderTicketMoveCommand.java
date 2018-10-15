package com.imooc.example.axon.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/26.
 */
public class OrderTicketMoveCommand {

    @TargetAggregateIdentifier
    private String ticketId;
    private String orderId;
    private String customerId;

    public OrderTicketMoveCommand(String ticketId, String orderId, String customerId) {
        this.ticketId = ticketId;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrderId() {
        return orderId;
    }
}
