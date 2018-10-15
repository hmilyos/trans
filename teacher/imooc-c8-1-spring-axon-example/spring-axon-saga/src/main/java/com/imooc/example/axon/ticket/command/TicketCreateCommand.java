package com.imooc.example.axon.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/28.
 */
public class TicketCreateCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String name;

    public TicketCreateCommand(String ticketId, String name) {
        this.ticketId = ticketId;
        this.name = name;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }
}
