package com.hmily.springtransjpa.service;

import com.hmily.springtransjpa.dao.CustomerRepository;
import com.hmily.springtransjpa.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@Slf4j
public class CustomerServiceTxInCode {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public Customer create(Customer customer){
        log.info(" create: {}", customer.getUsername());
        if (customer.getId() != null){
            throw new RuntimeException("用户已存在");
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            customer.setUsername("Code: " + customer.getUsername());
            customerRepository.save(customer);
            transactionManager.commit(status);
            return customer;
        } catch (Exception e){
            transactionManager.rollback(status);
            log.error(" create error: {}", e);
            throw e;
        }

    }
}
