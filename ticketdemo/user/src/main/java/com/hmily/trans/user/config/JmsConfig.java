package com.hmily.trans.user.config;

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

import javax.jms.ConnectionFactory;

/**
 * 配置 DB 和 MQ 的事务同步
 */
@Configuration
public class JmsConfig {
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
		proxy.setTargetConnectionFactory(factory);
//		同步事务
		proxy.setSynchedLocalTransactionAllowed(true);
		return proxy;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, 
			MessageConverter jacksonJmsMessageConverter) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);
//		这里不设置为 true ，在某些情况下会有问题
		jmsTemplate.setSessionTransacted(true);
		return jmsTemplate;
	}

//	为保证读消息时也采用这个 ConnectionFactory PlatformTransactionManager
	@Bean
	public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory cf,
			PlatformTransactionManager transactionManager, 
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, cf);
		factory.setReceiveTimeout(10000L);
//		factory.setCacheLevelName("CACHE_CONNECTION");
		factory.setTransactionManager(transactionManager);
		factory.setConcurrency("10");
		return factory;
	}

//	收发消息时自动把 Java 对象转换成 json
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}
