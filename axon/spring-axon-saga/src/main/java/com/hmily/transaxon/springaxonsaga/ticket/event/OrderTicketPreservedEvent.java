package com.hmily.transaxon.springaxonsaga.ticket.event;


public class OrderTicketPreservedEvent {

    private String orderId;
    private String customerId;
    private String ticketId;

    public OrderTicketPreservedEvent() {}

    public OrderTicketPreservedEvent(String orderId, String customerId, String ticketId) {
        this.orderId = orderId;
        this.ticketId = ticketId;
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
