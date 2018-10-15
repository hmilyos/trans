package com.imooc.example.axon.config;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.concurrent.Executors;

/**
 * Created by mavlarn on 2018/6/1.
 */
@Configuration
public class AxonConfig {

    @Autowired
    AxonConfiguration axonConfiguration;

//    注册一个独立的CommandHandler类的方法
//    @Bean
//    public OrderCommandHandler orderCommandHandler() {
//        Repository<Order> orderRepository = axonConfiguration.repository(Order.class);
//        Repository<Customer> customerRepository = axonConfiguration.repository(Customer.class);
//        return new OrderCommandHandler(orderRepository, customerRepository);
//    }

    @Autowired
    DataSource dataSource;

    @Autowired
    EntityManagerProvider entityManagerProvider;

    @Autowired
    TransactionManager transactionManager;

    @Bean
    public EventScheduler eventScheduler(EventBus eventBus, TransactionManager transactionManager) {
        return new SimpleEventScheduler(Executors.newScheduledThreadPool(1), eventBus, transactionManager);
    }
}
