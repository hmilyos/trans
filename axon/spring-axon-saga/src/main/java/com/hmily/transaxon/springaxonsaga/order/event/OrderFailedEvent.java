package com.hmily.transaxon.springaxonsaga.order.event;

public class OrderFailedEvent {
    private String reason;
    private String orderId;

    public OrderFailedEvent() {
    }

    public String getReason() {
        return reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderFailedEvent(String reason, String orderId) {

        this.reason = reason;
        this.orderId = orderId;
    }
}
