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
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void handle(String msg) {
        LOG.info("Get msg1:{}", msg);
        Customer customer = new Customer();
        customer.setUsername(msg);
        customer.setDeposit(100);
        customerRepository.save(customer);
        if (msg.contains("error1")) {
            throw new RuntimeException("Error1");
        }

        jmsTemplate.convertAndSend("customer:msg:reply", msg + " created.");
        if (msg.contains("error2")) {
            throw new RuntimeException("Error2");
        }
    }

    @Transactional
    public Customer create(Customer customer) {
        LOG.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        customer = customerRepository.save(customer);
        if (customer.getUsername().contains("error1")) {
            throw new RuntimeException("Error1");
        }
        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
        if (customer.getUsername().contains("error2")) {
            throw new RuntimeException("Error2");
        }

        return customer;
    }

}
