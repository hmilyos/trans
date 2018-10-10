package com.hmily.trans.springdtxdbdb.controller;

import com.hmily.trans.springdtxdbdb.domain.Order;
import com.hmily.trans.springdtxdbdb.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/order")
    public void create(@RequestBody Order order) {
        customerService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Map userInfo(@PathVariable Long id) {
        return customerService.userInfo(id);
    }

}
