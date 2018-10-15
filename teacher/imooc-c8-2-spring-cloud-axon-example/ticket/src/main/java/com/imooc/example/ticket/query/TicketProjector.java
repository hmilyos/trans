package com.imooc.example.ticket.query;

import com.imooc.example.ticket.event.saga.OrderTicketMovedEvent;
import com.imooc.example.ticket.event.saga.OrderTicketPreservedEvent;
import com.imooc.example.ticket.event.OrderTicketUnlockedEvent;
import com.imooc.example.ticket.event.TicketCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mavlarn on 2018/6/5.
 */
@Component
@ProcessingGroup("TicketEventProcessor")
public class TicketProjector {
    private static final Logger LOG = LoggerFactory.getLogger(TicketProjector.class);

    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @EventHandler
    public void on(TicketCreatedEvent event) {
        TicketEntity ticket = new TicketEntity();
        ticket.setId(event.getTicketId());
        ticket.setName(event.getName());
        ticketEntityRepository.save(ticket);
        LOG.info("TicketProjector Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onPreserve(OrderTicketPreservedEvent event) {
        String lockUser = event.getCustomerId();
        TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
        ticket.setLockUser(lockUser);
        ticketEntityRepository.save(ticket);
        LOG.info("TicketProjector Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onUnlock(OrderTicketUnlockedEvent event) {
        TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
        ticket.setLockUser(null);
        ticketEntityRepository.save(ticket);
        LOG.info("TicketProjector Executed event:{}", event);
    }

    @EventSourcingHandler
    public void onMove(OrderTicketMovedEvent event) {
        TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
        ticket.setOwner(event.getCustomerId());
        ticket.setLockUser(null);
        ticketEntityRepository.save(ticket);
        LOG.info("TicketProjector Executed event:{}", event);
    }
}
