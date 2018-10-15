package com.imooc.example.axon.ticket;

import com.imooc.example.axon.ticket.command.TicketCreateCommand;
import com.imooc.example.axon.ticket.query.TicketEntity;
import com.imooc.example.axon.ticket.query.TicketEntityRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mavlarn on 2018/5/28.
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @PostMapping("")
    public CompletableFuture<Object> create(@RequestParam String name) {
        LOG.info("Request to create ticket:{}", name);
        UUID ticketId = UUID.randomUUID();
        TicketCreateCommand command = new TicketCreateCommand(ticketId.toString(), name);
        return commandGateway.send(command);
    }

    @GetMapping("")
    public List<TicketEntity> all() {
        return ticketEntityRepository.findAll();
    }
}
