package com.imooc.example.axon.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/26.
 */
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
