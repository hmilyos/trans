package com.hmily.trans.springdtxjpadb.service;

import com.hmily.trans.springdtxjpadb.domain.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ICustomerService {
    @Transactional
    void createOrder(Order order);

    Map userInfo(Long customerId);
}
