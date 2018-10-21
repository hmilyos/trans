package com.hmily.transaxon.springaxonsaga.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;


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
