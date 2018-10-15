package com.immoc.example.springtx.service;

import com.immoc.example.springtx.dao.CustomerRepository;
import com.immoc.example.springtx.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mavlarn on 2018/1/24.
 */
@Service
public class CustomerServiceTxInAnnotation {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceTxInAnnotation.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public Customer create(Customer customer) {
        LOG.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
        return customerRepository.save(customer);
    }

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public Customer createByListener(String name) {
        LOG.info("CustomerService In Annotation by Listener create customer:{}", name);
        Customer customer = new Customer();
        customer.setUsername("Annotation:" + name);
        customer.setRole("USER");
        customer.setPassword("111111");

        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
        return customerRepository.save(customer);
    }

}
