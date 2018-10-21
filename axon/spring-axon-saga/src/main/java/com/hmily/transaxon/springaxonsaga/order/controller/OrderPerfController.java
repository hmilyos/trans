package com.hmily.transaxon.springaxonsaga.order.controller;

import com.hmily.transaxon.springaxonsaga.customer.query.CustomerEntity;
import com.hmily.transaxon.springaxonsaga.customer.query.CustomerProjector;
import com.hmily.transaxon.springaxonsaga.order.Order;
import com.hmily.transaxon.springaxonsaga.order.command.OrderCreateCommand;
import com.hmily.transaxon.springaxonsaga.ticket.query.TicketEntity;
import com.hmily.transaxon.springaxonsaga.ticket.query.TicketProjector;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderPerfController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderPerfController.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private TicketProjector ticketProjector;
    @Autowired
    private CustomerProjector customerProjector;

    private List<TicketEntity> ticketList;
    private List<CustomerEntity> customerList;

    @PostMapping("/test/oneUserAllTicket")
    public void oneUserAllTicket(@RequestBody Order order) {
        Random random = new Random();
        TicketEntity buyTicket = this.ticketList.get(random.nextInt(ticketList.size()));
        UUID orderId = UUID.randomUUID();
        OrderCreateCommand command = new OrderCreateCommand(orderId.toString(), order.getCustomerId(),
                order.getTitle(), buyTicket.getId(), order.getAmount());
        LOG.info("OneUserAllTicket Create Order:{}", command);
        commandGateway.send(command, LoggingCallback.INSTANCE);
    }

    @PostMapping("/test/allUserOneTicket")
    public void create(@RequestBody Order order) {
        Random random = new Random();
        CustomerEntity customer = this.customerList.get(random.nextInt(customerList.size()));
        UUID orderId = UUID.randomUUID();
        OrderCreateCommand command = new OrderCreateCommand(orderId.toString(), customer.getId(),
                order.getTitle(), order.getTicketId(), order.getAmount());
        LOG.info("AllUserOneTicket Create Order:{}", command);
        commandGateway.send(command, LoggingCallback.INSTANCE);
    }

    @GetMapping("/tickets")
    public List<TicketEntity> getAllTickets() {
        List<TicketEntity> tickets = ticketProjector.getAllTickets();
        this.ticketList = tickets.stream().filter(
                ticket ->
                        ticket.getLockUser() == null && ticket.getOwner() == null
        ).collect(Collectors.toList());
        return this.ticketList;
    }

    @GetMapping("/customers")
    public List<CustomerEntity> getAllCustomers() {
        this.customerList = customerProjector.getAllCustomers();
        return this.customerList;
    }


}
