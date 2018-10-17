package com.hmily.trans.user.service.impl;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.dto.TicketDTO;
import com.hmily.trans.common.service.IOrderCompositeService;
import com.hmily.trans.common.service.ITicketCompositeService;
import com.hmily.trans.user.dao.CustomerRepository;
import com.hmily.trans.user.domain.Customer;
import com.hmily.trans.user.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IOrderCompositeService orderClient;

    @Autowired
    private ITicketCompositeService ticketClient;

    @Override
    public void init() {
        if (customerRepository.count() > 0){
            return;
        }
        Customer customer = new Customer();
        customer.setUsername("hmily");
        customer.setPassword("111");
        customer.setRole("User");
        customer.setDeposit(1000);
        customerRepository.save(customer);
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Map getMyInfo(String username) {
        Customer customer = customerRepository.findOneByUsername(username);
        List<OrderDTO> orders = orderClient.getMyOrder(customer.getId());
        List<TicketDTO> tickets = ticketClient.getMyTickets(customer.getId());
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        result.put("tickets", tickets);
        return result;
    }
}
