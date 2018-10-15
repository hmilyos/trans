package com.imooc.example.springdtx.web;

import com.imooc.example.springdtx.dao.CustomerRepository;
import com.imooc.example.springdtx.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("")
    @Secured("ROLE_ADMIN")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
