package com.imooc.example.axon.ticket;

import com.imooc.example.axon.ticket.event.OrderTicketMovedEvent;
import com.imooc.example.axon.ticket.event.OrderTicketPreserveFailedEvent;
import com.imooc.example.axon.ticket.event.OrderTicketPreservedEvent;
import com.imooc.example.axon.ticket.event.OrderTicketUnlockedEvent;
import com.imooc.example.axon.ticket.command.OrderTicketMoveCommand;
import com.imooc.example.axon.ticket.command.OrderTicketPreserveCommand;
import com.imooc.example.axon.ticket.command.OrderTicketUnlockCommand;
import com.imooc.example.axon.ticket.command.TicketCreateCommand;
import com.imooc.example.axon.ticket.event.TicketCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Ticket {

    private static final Logger LOG = LoggerFactory.getLogger(Ticket.class);

    @AggregateIdentifier
    private String id;

    private String name;

    private String lockUser;

    private String owner;

    public Ticket() {
    }

    @CommandHandler
    public Ticket(TicketCreateCommand command) {
        apply(new TicketCreatedEvent(command.getTicketId(), command.getName()));
    }

    @CommandHandler
    public void handle(OrderTicketPreserveCommand command) {
        if (this.owner != null) {
            LOG.error("Ticket is owned.");
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
        } else if (this.lockUser != null && this.lockUser.equals(command.getCustomerId())) {
            LOG.info("duplicated command");
        } else if (this.lockUser == null) {
            apply(new OrderTicketPreservedEvent(command.getOrderId(), command.getCustomerId(), command.getTicketId()));
        } else {
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
        }
    }

    @CommandHandler
    public void handle(OrderTicketUnlockCommand command) {
        if (this.lockUser == null) {
            LOG.error("Invalid command, ticket not locked");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            LOG.error("Invalid command, ticket not locked by:{}", command.getCustomerId());
        } else {
            apply(new OrderTicketUnlockedEvent(command.getTicketId()));
        }
    }

    @CommandHandler
    public void handle(OrderTicketMoveCommand command) {
        if (this.lockUser == null) {
            LOG.error("Invalid command, ticket not locked");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            LOG.error("Invalid command, ticket not locked by:{}", command.getCustomerId());
        } else {
            apply(new OrderTicketMovedEvent(command.getOrderId(), command.getTicketId(), command.getCustomerId()));
        }
    }

    @EventSourcingHandler
    public void onCreate(TicketCreatedEvent event) {
        this.id = event.getTicketId();
        this.name = event.getName();
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onPreserve(OrderTicketPreservedEvent event) {
        this.lockUser = event.getCustomerId();
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onUnlock(OrderTicketUnlockedEvent event) {
        this.lockUser = null;
        LOG.info("Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onMove(OrderTicketMovedEvent event) {
        this.lockUser = null;
        this.owner = event.getCustomerId();
        LOG.info("Executed event:{}", event);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLockUser() {
        return lockUser;
    }

    public String getOwner() {
        return owner;
    }
}
