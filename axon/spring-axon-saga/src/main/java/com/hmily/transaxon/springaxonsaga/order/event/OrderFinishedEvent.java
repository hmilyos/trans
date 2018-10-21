package com.hmily.transaxon.springaxonsaga.order.event;

public class OrderFinishedEvent {
    private String orderId;

    public OrderFinishedEvent() {}

    public OrderFinishedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
