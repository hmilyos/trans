package com.imooc.example.axon.order.event;

/**
 * Created by mavlarn on 2018/5/27.
 */
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
