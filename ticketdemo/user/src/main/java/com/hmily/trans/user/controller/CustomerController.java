package com.hmily.trans.user.controller;

import com.hmily.trans.user.domain.Customer;
import com.hmily.trans.user.service.ICustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @PostConstruct
    public void init(){
        customerService.init();
    }

    @PostMapping("")
    public Customer create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @GetMapping("")
    @HystrixCommand
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/my")
    @HystrixCommand
    public Map getMyInfo() {
        return customerService.getMyInfo("hmily");
    }
}
