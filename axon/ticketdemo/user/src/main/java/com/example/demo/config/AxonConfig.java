package com.example.demo.config;

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



@Configuration
public class AxonConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AxonConfig.class);

    @Value("${axon.amqp.exchange}")
    private String exchangeName;

    @Bean
    public Queue userQueue(){
        return new Queue("user",true);
    }

    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder.bind(userQueue()).to(exchange()).with("com.imooc.example.user.event.#").noargs();
    }

    @Bean
    public Queue ticketSagaQueue(){
        return new Queue("saga", true);
    }

    @Bean
    public Binding ticketSagaQueueBinding() {
        return BindingBuilder.bind(ticketSagaQueue()).to(exchange()).with("com.imooc.example.user.event.saga.#").noargs();
    }

    @Bean
    public SpringAMQPMessageSource userMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer){
            @RabbitListener(queues = "user")
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
        ehConfig.registerSubscribingEventProcessor("UserEventProcessor", c -> userMessageSource);
    }

}
