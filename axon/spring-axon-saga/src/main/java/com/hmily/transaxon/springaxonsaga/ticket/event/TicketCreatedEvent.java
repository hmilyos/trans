package com.hmily.transaxon.springaxonsaga.ticket.event;


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
