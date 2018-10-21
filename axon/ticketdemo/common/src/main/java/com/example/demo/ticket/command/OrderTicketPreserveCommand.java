package com.example.demo.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;


public class OrderTicketPreserveCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String orderId;
    private String customerId;

    public OrderTicketPreserveCommand(String orderId, String ticketId, String customerId) {
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
