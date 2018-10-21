package com.hmily.transaxon.springaxonsaga.order;

import com.hmily.transaxon.springaxonsaga.customer.command.OrderPayCommand;
import com.hmily.transaxon.springaxonsaga.customer.event.OrderPaidEvent;
import com.hmily.transaxon.springaxonsaga.customer.event.OrderPayFailedEvent;
import com.hmily.transaxon.springaxonsaga.order.command.OrderFailCommand;
import com.hmily.transaxon.springaxonsaga.order.command.OrderFinishCommand;
import com.hmily.transaxon.springaxonsaga.order.event.OrderCreatedEvent;
import com.hmily.transaxon.springaxonsaga.order.event.OrderFailedEvent;
import com.hmily.transaxon.springaxonsaga.order.event.OrderFinishedEvent;
import com.hmily.transaxon.springaxonsaga.ticket.command.OrderTicketMoveCommand;
import com.hmily.transaxon.springaxonsaga.ticket.command.OrderTicketPreserveCommand;
import com.hmily.transaxon.springaxonsaga.ticket.command.OrderTicketUnlockCommand;
import com.hmily.transaxon.springaxonsaga.ticket.event.OrderTicketMovedEvent;
import com.hmily.transaxon.springaxonsaga.ticket.event.OrderTicketPreserveFailedEvent;
import com.hmily.transaxon.springaxonsaga.ticket.event.OrderTicketPreservedEvent;
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

        OrderFailCommand failCommand = new OrderFailCommand(event.getOrderId(), "扣费失败");
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
    public static Logger getLOG() {
        return LOG;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    public EventScheduler getEventScheduler() {
        return eventScheduler;
    }

    public void setEventScheduler(EventScheduler eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ScheduleToken getTimeoutToken() {
        return timeoutToken;
    }

    public void setTimeoutToken(ScheduleToken timeoutToken) {
        this.timeoutToken = timeoutToken;
    }
}
