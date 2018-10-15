package com.imooc.example.axon.ticket.event;

/**
 * Created by mavlarn on 2018/5/28.
 */
public class TicketCreatedEvent {

    private String ticketId;

    private String name;

    public TicketCreatedEvent() {}

    public TicketCreatedEvent(String ticketId, String name) {
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
