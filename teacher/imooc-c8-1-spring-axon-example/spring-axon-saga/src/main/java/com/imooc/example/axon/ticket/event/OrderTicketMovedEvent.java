package com.imooc.example.axon.ticket.event;

/**
 * Created by mavlarn on 2018/5/26.
 */
public class OrderTicketMovedEvent {

    private String orderId;
    private String ticketId;

    private String customerId;

    public OrderTicketMovedEvent() {}

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
