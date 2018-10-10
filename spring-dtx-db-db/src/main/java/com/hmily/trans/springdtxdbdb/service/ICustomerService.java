package com.hmily.trans.springdtxdbdb.service;

import com.hmily.trans.springdtxdbdb.domain.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ICustomerService {
    @Transactional
    void createOrder(Order order);

    Map userInfo(Long customerId);
}
