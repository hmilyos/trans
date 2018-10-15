package com.imooc.example.user.event.saga;

/**
 * Created by mavlarn on 2018/5/27.
 */
public class OrderPayFailedEvent {

    private String orderId;

    public OrderPayFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
