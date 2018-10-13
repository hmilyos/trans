package com.hmily.trans.springdtxjmsdb.service;

import com.hmily.trans.springdtxjmsdb.dao.CustomerRepository;
import com.hmily.trans.springdtxjmsdb.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void handle(String username){
        log.info("Get msg1:{}", username);
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setDeposit(100);
        customerRepository.save(customer);
        if (username.contains("error1")) {
            throw new RuntimeException("Error1");
        }
        jmsTemplate.convertAndSend("customer:msg:reply", username + " created.");
        if (username.contains("error2")) {
            throw new RuntimeException("Error2");
        }
    }

    @Transactional
    public Customer create(Customer customer){
        log.info("CustomerService In Annotation create customer:{}", customer.getUsername());
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
