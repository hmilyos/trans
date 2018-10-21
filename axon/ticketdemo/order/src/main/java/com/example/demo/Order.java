package com.example.demo;


import com.example.demo.command.OrderCreateCommand;
import com.example.demo.command.OrderFailCommand;
import com.example.demo.command.OrderFinishCommand;
import com.example.demo.order.event.OrderFinishedEvent;
import com.example.demo.order.event.OrderFailedEvent;
import com.example.demo.order.event.saga.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


@Aggregate
public class Order {

    private static final Logger LOG = LoggerFactory.getLogger(Order.class);

    @AggregateIdentifier
    private String id;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

    private String status;

    private String reason;

    private ZonedDateTime createdDate;

    public Order() {
    }

    @CommandHandler
    public Order(OrderCreateCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId(), command.getCustomerId(), command.getTitle(), command.getTicketId(), command.getAmount(), ZonedDateTime.now()));
    }

    @CommandHandler
    public void handleFinish(OrderFinishCommand command) {
        apply(new OrderFinishedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handleFail(OrderFailCommand command) {
        apply(new OrderFailedEvent(command.getOrderId(), command.getReason()));
    }

    @EventSourcingHandler
    public void onCreate(OrderCreatedEvent event) {
        this.id = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.title = event.getTitle();
        this.ticketId = event.getTicketId();
        this.amount = event.getAmount();
        this.status = "NEW";
        this.createdDate = event.getCreatedDate();
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onFinished(OrderFinishedEvent event) {
        this.status = "FINISH";
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onFailed(OrderFailedEvent event) {
        this.status = "FAILED";
        this.reason = event.getReason();
        LOG.info("Executed event:{}", event);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", title='" + title + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
