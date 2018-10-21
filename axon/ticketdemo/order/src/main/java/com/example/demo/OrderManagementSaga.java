/*
 * Copyright (c) 2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo;

import com.example.demo.command.OrderFailCommand;
import com.example.demo.command.OrderFinishCommand;
import com.example.demo.order.event.OrderFailedEvent;
import com.example.demo.order.event.OrderFinishedEvent;
import com.example.demo.order.event.saga.OrderCreatedEvent;
import com.example.demo.ticket.command.OrderTicketMoveCommand;
import com.example.demo.ticket.command.OrderTicketPreserveCommand;
import com.example.demo.ticket.command.OrderTicketUnlockCommand;
import com.example.demo.ticket.event.saga.OrderTicketMovedEvent;
import com.example.demo.ticket.event.saga.OrderTicketPreserveFailedEvent;
import com.example.demo.ticket.event.saga.OrderTicketPreservedEvent;
import com.example.demo.user.command.OrderPayCommand;
import com.example.demo.user.saga.OrderPaidEvent;
import com.example.demo.user.saga.OrderPayFailedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Saga
public class OrderManagementSaga {

    private static final Logger LOG = LoggerFactory.getLogger(OrderManagementSaga.class);

    @Autowired
    private transient CommandBus commandBus;
    @Autowired
    private transient EventScheduler eventScheduler;

    private String orderId;
    private String customerId;
    private String ticketId;
    private Double amount;

    private ScheduleToken timeoutToken;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.ticketId = event.getTicketId();
        this.amount = event.getAmount();

        timeoutToken = eventScheduler.schedule(Instant.now().plusSeconds(60), new OrderPayFailedEvent(this.orderId));

        OrderTicketPreserveCommand command = new OrderTicketPreserveCommand(orderId, ticketId, customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreservedEvent event) {
        OrderPayCommand command = new OrderPayCommand(orderId, customerId, amount);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderTicketPreserveFailedEvent event) {
        OrderFailCommand command = new OrderFailCommand(event.getOrderId(), "Preserve Failed");
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidEvent event) {
        OrderTicketMoveCommand command = new OrderTicketMoveCommand(this.ticketId, this.orderId, this.customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPayFailedEvent event) {
        OrderTicketUnlockCommand command = new OrderTicketUnlockCommand(ticketId, customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);

        OrderFailCommand failCommand = new OrderFailCommand(event.getOrderId(), "Pay Failed");
        commandBus.dispatch(asCommandMessage(failCommand), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketMovedEvent event) {
        OrderFinishCommand command = new OrderFinishCommand(orderId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderFinishedEvent event) {
        LOG.info("Order:{} finished.", event.getOrderId());
        if (this.timeoutToken != null) {
            eventScheduler.cancelSchedule(this.timeoutToken);
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderFailedEvent event) {
        LOG.info("Order:{} failed.", event.getOrderId());
        if (this.timeoutToken != null) {
            eventScheduler.cancelSchedule(this.timeoutToken);
        }
    }

}
