package com.imooc.example.axon.order.event;

/**
 * Created by mavlarn on 2018/5/27.
 */
public class OrderFailedEvent {

    private String reason;
    private String orderId;

    public OrderFailedEvent() {}

    public OrderFailedEvent(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}
