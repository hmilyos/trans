package com.imooc.example.ticket.config;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by mavlarn on 2018/6/4.
 */
@Configuration
public class AxonConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AxonConfig.class);

    @Value("${axon.amqp.exchange}")
    private String exchangeName;

    @Bean
    public Queue ticketQueue(){
        return new Queue("ticket", true);
    }


    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Binding ticketQueueBinding() {
        return BindingBuilder.bind(ticketQueue()).to(exchange()).with("com.imooc.example.ticket.event.#").noargs();
    }

    @Bean
    public Queue ticketSagaQueue(){
        return new Queue("saga", true);
    }

    @Bean
    public Binding ticketSagaQueueBinding() {
        return BindingBuilder.bind(ticketSagaQueue()).to(exchange()).with("com.imooc.example.ticket.event.saga.#").noargs();
    }

    @Bean
    public SpringAMQPMessageSource ticketMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer){
            @RabbitListener(queues = "ticket")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                LOG.debug("Message received: {}", message);
                super.onMessage(message, channel);
            }
        };
    }

    @Autowired
    public void configure(EventHandlingConfiguration ehConfig, SpringAMQPMessageSource userMessageSource,
                          SpringAMQPMessageSource ticketMessageSource, SpringAMQPMessageSource orderMessageSource) {
        ehConfig.registerSubscribingEventProcessor("TicketEventProcessor", c -> ticketMessageSource);
    }

}
