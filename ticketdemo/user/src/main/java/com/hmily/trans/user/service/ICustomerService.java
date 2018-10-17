package com.hmily.trans.user.service;

import com.hmily.trans.user.domain.Customer;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    void init();

    Customer create(Customer customer);

    List<Customer> getAll();

    Map getMyInfo(String username);
}
