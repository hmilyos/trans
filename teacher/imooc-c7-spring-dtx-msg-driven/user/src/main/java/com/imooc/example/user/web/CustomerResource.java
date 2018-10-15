package com.imooc.example.user.web;

import com.imooc.example.dto.OrderDTO;
import com.imooc.example.dto.TicketDTO;
import com.imooc.example.service.OrderCompositeService;
import com.imooc.example.service.TicketCompositeService;
import com.imooc.example.user.dao.CustomerRepository;
import com.imooc.example.user.domain.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @PostConstruct
    public void init() {
        if (customerRepository.count() > 0) {
            return;
        }
        Customer customer = new Customer();
        customer.setUsername("imooc");
        customer.setPassword("111111");
        customer.setRole("User");
        customer.setDeposit(1000);
        customerRepository.save(customer);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderCompositeService orderClient;
    @Autowired
    private TicketCompositeService ticketClient;

    @PostMapping("")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @HystrixCommand
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/my")
    @HystrixCommand
    public Map getMyInfo() {
        Customer customer = customerRepository.findOneByUsername("imooc");
        List<OrderDTO> orders = orderClient.getMyOrder(customer.getId());
        List<TicketDTO> tickets = ticketClient.getMyTickets(customer.getId());
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        result.put("tickets", tickets);
        return result;
    }

}
