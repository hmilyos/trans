package com.hmily.trans.springdtxjpadb.service;

import com.hmily.trans.springdtxjpadb.dao.CustomerRepository;
import com.hmily.trans.springdtxjpadb.domain.Customer;
import com.hmily.trans.springdtxjpadb.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String SQL_CREATE_ORDER = "INSERT INTO customer_order (customer_id, title, amount) VALUES (?, ?, ?)";


    @Override
    public void createOrder(Order order) {
        Customer customer = customerRepository.findOne(order.getCustomerId());
        customer.setDeposit(customer.getDeposit() - order.getAmount());
        customerRepository.save(customer); if (order.getTitle().contains("error1")) {
            throw new RuntimeException("Error1");
        }
//        如果使用默认的，不配置链式事务的话，下面这个使用了另一个数据源，是不在这个事务范围内的
//        配置了就能进行回滚了，两个提交顺序跟配置链式事务时，ChainedTransactionManager 的顺序有关，放在后面的先提交
//        创建事务的顺序 和 ChainedTransactionManager顺序有关，前面的先创建事务
        orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getCustomerId(), order.getTitle(), order.getAmount());
        if (order.getTitle().contains("error2")) {
            throw new RuntimeException("Error2");
        }
    }

    @Override
    public Map userInfo(Long customerId) {
        Customer customer = customerRepository.findOne(customerId);
        List orders = orderJdbcTemplate.queryForList("SELECT * from customer_order where customer_id = " + customerId);

        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        return result;
    }
}
