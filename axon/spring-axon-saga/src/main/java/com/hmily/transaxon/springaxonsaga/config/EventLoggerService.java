package com.hmily.transaxon.springaxonsaga.config;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class EventLoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLoggerService.class);

    @EventHandler
    public void handle(final Object event) {
        LOGGER.debug("Event: {}", event.getClass().getCanonicalName());
        LOGGER.debug("       {}", event);

        if (event instanceof GenericEventMessage) {
            final GenericEventMessage<?> message = (GenericEventMessage<?>) event;
            final Object payload = message.getPayload();
            LOGGER.debug("Payload: {}", payload.getClass().getCanonicalName());
            LOGGER.debug("         {}", payload);
        }
    }
}
