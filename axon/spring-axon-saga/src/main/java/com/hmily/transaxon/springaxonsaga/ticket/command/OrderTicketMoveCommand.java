package com.hmily.transaxon.springaxonsaga.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;


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
