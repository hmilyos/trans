package com.immoc.example.springtx.web;

import com.immoc.example.springtx.dao.CustomerRepository;
import com.immoc.example.springtx.domain.Customer;
import com.immoc.example.springtx.service.CustomerServiceTxInAnnotation;
import com.immoc.example.springtx.service.CustomerServiceTxInCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    private CustomerServiceTxInAnnotation customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in annotation create customer:{}", customer.getUsername());
        return customerService.create(customer);
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in code create customer:{}", customer.getUsername());
        return customerServiceInCode.create(customer);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
