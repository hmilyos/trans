package com.hmily.springtransjtamulti.controller;

import com.hmily.springtransjtamulti.dao.CustomerRepository;
import com.hmily.springtransjtamulti.domain.Customer;
import com.hmily.springtransjtamulti.service.CustomerServiceTxInAnnotation;
import com.hmily.springtransjtamulti.service.CustomerServiceTxInCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerServiceTxInAnnotation customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        log.info("CustomerResource create in annotation create customer:{}", customer.getUsername());
        return customerService.create(customer);
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        log.info("CustomerResource create in code create customer:{}", customer.getUsername());
        return customerServiceInCode.create(customer);
    }

    @Transactional
    @PostMapping("/message/annotation")
    public void createMsgWithListener(@RequestParam String userName) {
        jmsTemplate.convertAndSend("customer:msg:new", userName);
    }

    @Transactional
    @PostMapping("/message/code")
    public void createMsgDirect(@RequestParam String userName) {
        jmsTemplate.convertAndSend("customer:msg2:new", userName);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
