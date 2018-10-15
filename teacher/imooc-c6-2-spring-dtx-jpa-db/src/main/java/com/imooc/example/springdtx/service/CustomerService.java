package com.imooc.example.springdtx.service;

import com.imooc.example.springdtx.dao.CustomerRepository;
import com.imooc.example.springdtx.domain.Customer;
import com.imooc.example.springdtx.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mavlarn on 2018/3/5.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String SQL_CREATE_ORDER = "INSERT INTO customer_order (customer_id, title, amount) VALUES (?, ?, ?)";

    @Transactional
    public void createOrder(Order order) {
        Customer customer = customerRepository.findOne(order.getCustomerId());
        customer.setDeposit(customer.getDeposit() - order.getAmount());
        customerRepository.save(customer);
        if (order.getTitle().contains("error1")) {
            throw new RuntimeException("Error1");
        }
        orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getCustomerId(), order.getTitle(), order.getAmount());

        if (order.getTitle().contains("error2")) {
            throw new RuntimeException("Error2");
        }
    }


    public Map userInfo(Long customerId) {
        Customer customer = customerRepository.findOne(customerId);
        List orders = orderJdbcTemplate.queryForList("SELECT * from customer_order where customer_id = " + customerId);

        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        return result;
    }

}
