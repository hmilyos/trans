package com.imooc.example.ticket.event;

/**
 * Created by mavlarn on 2018/5/27.
 */
public class OrderTicketUnlockedEvent {

    private String ticketId;

    public OrderTicketUnlockedEvent(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketId() {
        return ticketId;
    }
}
