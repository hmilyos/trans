package com.imooc.example.axon.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/26.
 */
public class OrderTicketUnlockCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String customerId;

    public OrderTicketUnlockCommand(String ticketId, String customerId) {
        this.ticketId = ticketId;
        this.customerId = customerId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
