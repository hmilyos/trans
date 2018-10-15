package com.imooc.example.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/24.
 */
public class OrderCreateCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String customerId;
    private String title;
    private String ticketId;
    private Double amount;

    public OrderCreateCommand(String orderId, String customerId, String title, String ticketId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.title = title;
        this.ticketId = ticketId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTitle() {
        return title;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "OrderCreateCommand{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", title='" + title + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
