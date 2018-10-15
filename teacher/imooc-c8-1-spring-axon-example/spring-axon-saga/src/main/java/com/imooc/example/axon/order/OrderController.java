package com.imooc.example.axon.order;

import com.imooc.example.axon.order.command.OrderCreateCommand;
import com.imooc.example.axon.order.query.OrderEntity;
import com.imooc.example.axon.order.query.OrderEntityRepository;
import com.imooc.example.axon.order.query.OrderId;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by mavlarn on 2018/5/28.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @PostMapping("")
    public void create(@RequestBody Order order) {
        UUID orderId = UUID.randomUUID();
        OrderCreateCommand command = new OrderCreateCommand(orderId.toString(), order.getCustomerId(),
                order.getTitle(), order.getTicketId(), order.getAmount());
        commandGateway.send(command, LoggingCallback.INSTANCE);
    }

    @GetMapping("/query/{orderId}")
    public Order get(@PathVariable String orderId) throws ExecutionException, InterruptedException {
        return queryGateway.query(new OrderId(orderId), Order.class).get();
    }

    @GetMapping("/{orderId}")
    public OrderEntity getView(@PathVariable String orderId) {
        return orderEntityRepository.findOne(orderId);
    }
}
