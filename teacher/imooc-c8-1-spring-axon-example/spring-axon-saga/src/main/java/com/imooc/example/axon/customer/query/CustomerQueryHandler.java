package com.imooc.example.axon.customer.query;

import com.imooc.example.axon.customer.Customer;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mavlarn on 2018/5/29.
 */
@Component
public class CustomerQueryHandler {


    @Autowired
    AxonConfiguration axonConfiguration;

    @QueryHandler
    public Customer query(CustomerId customerId) {
        // WARN: 强烈不建议使用这种方式将聚合数据暴露给外界，而应该使用物化视图的方式将保存的视图数据显示出来。
        // 这里这样做，只是用于debug，有时候，可能写的代码有问题，导致聚合数据跟视图数据不一致。
        final Customer[] customers = new Customer[1];
        Repository<Customer> customerRepository = axonConfiguration.repository(Customer.class);
        customerRepository.load(customerId.toString()).execute((Customer customer) -> customers[0] = customer);

        return customers[0];
    }
}
