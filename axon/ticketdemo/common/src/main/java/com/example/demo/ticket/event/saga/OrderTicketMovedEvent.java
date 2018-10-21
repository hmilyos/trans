package com.example.demo.ticket.event.saga;


public class OrderTicketMovedEvent {

    private String orderId;
    private String ticketId;

    private String customerId;

    public OrderTicketMovedEvent(String orderId, String ticketId, String customerId) {
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
