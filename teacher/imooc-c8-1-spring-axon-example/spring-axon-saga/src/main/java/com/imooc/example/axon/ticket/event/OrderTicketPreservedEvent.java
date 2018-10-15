package com.imooc.example.axon.ticket.event;

/**
 * Created by mavlarn on 2018/5/26.
 */
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
