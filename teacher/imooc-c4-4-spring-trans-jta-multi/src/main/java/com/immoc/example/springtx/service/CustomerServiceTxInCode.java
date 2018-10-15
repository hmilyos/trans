package com.immoc.example.springtx.service;

import com.immoc.example.springtx.dao.CustomerRepository;
import com.immoc.example.springtx.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by mavlarn on 2018/1/24.
 */
@Service
public class CustomerServiceTxInCode {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceTxInCode.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private JmsTemplate jmsTemplate;

    public Customer create(Customer customer) {
        LOG.info("CustomerService In Code create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            customer.setUsername("Code:" + customer.getUsername());
            customerRepository.save(customer);
            jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
            transactionManager.commit(status);
            return customer;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @JmsListener(destination = "customer:msg2:new")
    public void createByListener(String name) {
        LOG.info("CustomerService In Code by Listener create customer:{}", name);
        Customer customer = new Customer();
        customer.setUsername("Code:" + name);
        customer.setRole("USER");
        customer.setPassword("111111");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            customerRepository.save(customer);
            jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
