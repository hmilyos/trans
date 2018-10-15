package com.imooc.example.axon.ticket.event;

/**
 * Created by mavlarn on 2018/5/26.
 */
public class OrderTicketPreserveFailedEvent {

    private String orderId;

    public OrderTicketPreserveFailedEvent() {}

    public OrderTicketPreserveFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
