package com.imooc.example.axon.customer.event;

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

    @Override
    public String toString() {
        return "OrderPayFailedEvent{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
