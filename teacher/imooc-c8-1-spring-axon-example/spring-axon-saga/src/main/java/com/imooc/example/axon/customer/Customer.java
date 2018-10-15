package com.imooc.example.axon.customer;

import com.imooc.example.axon.customer.command.CustomerChargeCommand;
import com.imooc.example.axon.customer.command.CustomerCreateCommand;
import com.imooc.example.axon.customer.command.CustomerDepositCommand;
import com.imooc.example.axon.customer.event.CustomerChargedEvent;
import com.imooc.example.axon.customer.event.CustomerCreatedEvent;
import com.imooc.example.axon.customer.event.CustomerDepositedEvent;
import com.imooc.example.axon.customer.command.OrderPayCommand;
import com.imooc.example.axon.customer.event.OrderPaidEvent;
import com.imooc.example.axon.customer.event.OrderPayFailedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by mavlarn on 2018/5/24.
 */
@Aggregate
public class Customer {

    private static final Logger LOG = LoggerFactory.getLogger(Customer.class);

    @AggregateIdentifier
    private String customerId;

    private String username;

    private String password;

    private Double deposit;

    public Customer() {
    }

    @CommandHandler
    public Customer(CustomerCreateCommand command) {
        apply(new CustomerCreatedEvent(command.getCustomerId(), command.getName(), command.getPassword()));
    }

    @CommandHandler
    public void handle(CustomerDepositCommand command) {
        apply(new CustomerDepositedEvent(command.getCustomerId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(CustomerChargeCommand command) {
        if (deposit - command.getAmount() >= 0) {
            apply(new CustomerChargedEvent(command.getCustomerId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }

    @CommandHandler
    public void handle(OrderPayCommand command) {
        if (command.getAmount() == 0) {
            // do nothing, test the Scheduled Event.
        } else if (this.deposit < command.getAmount()) {
            LOG.error("Not enough deposit");
            apply(new OrderPayFailedEvent(command.getOrderId()));
        } else {
            apply(new OrderPaidEvent(command.getOrderId(), command.getCustomerId(), command.getAmount()));
        }
    }


    @EventSourcingHandler
    protected void on(CustomerCreatedEvent event) {
        this.customerId = event.getCustomerId();
        this.username = event.getName();
        this.password = event.getPassword();
        this.deposit = 0d;
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    protected void on(CustomerDepositedEvent event) {
        this.deposit = deposit + event.getAmount();
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    protected void on(CustomerChargedEvent event) {
        this.deposit = deposit - event.getAmount();
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    protected void on(OrderPaidEvent event) {
        this.deposit = deposit - event.getAmount();
        LOG.info("Executed event:{}", event);
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Double getDeposit() {
        return deposit;
    }
}
