package com.immoc.example.springtx.web;

import com.immoc.example.springtx.dao.CustomerRepository;
import com.immoc.example.springtx.domain.Customer;
import com.immoc.example.springtx.service.CustomerService;
import com.immoc.example.springtx.service.CustomerServiceTxInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/msg")
    public void create(@RequestBody Customer customer) {
        jmsTemplate.convertAndSend("customer:msg:new", customer.getUsername());
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        return customerServiceInCode.create(customer);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/msg")
    public String getMsg() {
        jmsTemplate.setReceiveTimeout(3000);
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }


}
