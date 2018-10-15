package com.immoc.example.springtx.service;

import com.immoc.example.springtx.dao.CustomerRepository;
import com.immoc.example.springtx.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by mavlarn on 2018/1/24.
 */
@Service
public class CustomerServiceTxInCode {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceTxInCode.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public Customer create(Customer customer) {
        LOG.info("CustomerService In Code create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            customer.setUsername("Code:" + customer.getUsername());
            customerRepository.save(customer);
            transactionManager.commit(status);
            return customer;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
