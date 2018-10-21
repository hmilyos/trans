package com.hmily.transaxon.springaxonsaga.customer.event;

public class OrderPayFailedEvent {

    private String orderId;

    public OrderPayFailedEvent() {
    }

    public OrderPayFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "OrderPayFailedEvent{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
