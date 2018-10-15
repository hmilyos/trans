package com.imooc.example.springdtx.web;

import com.imooc.example.springdtx.domain.Order;
import com.imooc.example.springdtx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/order")
    public void create(@RequestBody Order order) {
        customerService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Map userInfo(@PathVariable Long id) {
        return customerService.userInfo(id);
    }

}
