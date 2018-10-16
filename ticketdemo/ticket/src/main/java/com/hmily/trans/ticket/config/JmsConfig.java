package com.hmily.trans.ticket.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JmsConfig {
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
		proxy.setTargetConnectionFactory(factory);
		proxy.setSynchedLocalTransactionAllowed(true);
		return proxy;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, 
			MessageConverter jacksonJmsMessageConverter) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);
		jmsTemplate.setSessionTransacted(true);
		return jmsTemplate;
	}

	@Bean
	public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory cf,
			PlatformTransactionManager transactionManager, 
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, cf);
		factory.setReceiveTimeout(10000L);
		factory.setCacheLevelName("CACHE_CONNECTION");
		factory.setTransactionManager(transactionManager);
		factory.setConcurrency("10");
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}
