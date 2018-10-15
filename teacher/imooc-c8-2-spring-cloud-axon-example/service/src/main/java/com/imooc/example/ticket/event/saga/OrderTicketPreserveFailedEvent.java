package com.imooc.example.ticket.event.saga;

/**
 * Created by mavlarn on 2018/5/26.
 */
public class OrderTicketPreserveFailedEvent {

    private String orderId;

    public OrderTicketPreserveFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
