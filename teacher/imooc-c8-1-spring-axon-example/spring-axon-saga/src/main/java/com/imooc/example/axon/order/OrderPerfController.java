package com.imooc.example.axon.order;

import com.imooc.example.axon.customer.query.CustomerEntity;
import com.imooc.example.axon.customer.query.CustomerEntityRepository;
import com.imooc.example.axon.order.command.OrderCreateCommand;
import com.imooc.example.axon.ticket.query.TicketEntity;
import com.imooc.example.axon.ticket.query.TicketEntityRepository;
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

/**
 * Created by mavlarn on 2018/5/28.
 */
@RestController
@RequestMapping("/orders")
public class OrderPerfController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderPerfController.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private TicketEntityRepository ticketEntityRepository;
    @Autowired
    private CustomerEntityRepository customerEntityRepository;

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
        List<TicketEntity> tickets = ticketEntityRepository.findAll();
        this.ticketList = tickets.stream().filter(ticket -> ticket.getLockUser() == null && ticket.getOwner() == null).collect(Collectors.toList());
        return this.ticketList;
    }

    @GetMapping("/customers")
    public List<CustomerEntity> getAllCustomers() {
        this.customerList = customerEntityRepository.findAll();
        return this.customerList;
    }
}
