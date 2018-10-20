package com.hmily.trans.user.service.impl;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.dto.TicketDTO;
import com.hmily.trans.common.service.IOrderCompositeService;
import com.hmily.trans.common.service.ITicketCompositeService;
import com.hmily.trans.user.dao.CustomerRepository;
import com.hmily.trans.user.dao.PayInfoRepository;
import com.hmily.trans.user.domain.Customer;
import com.hmily.trans.user.domain.PayInfo;
import com.hmily.trans.user.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IOrderCompositeService orderClient;

    @Autowired
    private ITicketCompositeService ticketClient;

    @Autowired
    private PayInfoRepository payInfoRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

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

//    监听支付的消息
    @Transactional
    @JmsListener(destination = "order:pay", containerFactory = "msgFactory")
    public void handle(OrderDTO msg) {
        LOG.info("Get new order to pay:{}", msg);
        // 先检查payInfo判断重复消息。
        PayInfo pay = payInfoRepository.findOneByOrderId(msg.getId());
        if (pay != null) {
//            已经支付过了，重复消息
            LOG.warn("Order already paid, duplicated message.");
        } else {
            Customer customer = customerRepository.findOne(msg.getCustomerId());
            if (customer.getDeposit() < msg.getAmount()) {
//            余额不足，不够本次支付
                LOG.info("No enough deposit, need amount:{}", msg.getAmount());
                msg.setStatus("NOT_ENOUGH_DEPOSIT");
                jmsTemplate.convertAndSend("order:ticket_error", msg);
                return;
            }
            pay = new PayInfo();
            pay.setOrderId(msg.getId());
            pay.setAmount(msg.getAmount());
            pay.setStatus("PAID");
            payInfoRepository.save(pay);
//        customer.setDeposit(customer.getDeposit() - msg.getAmount());
//        customerRepository.save(customer); // 如果用户下了2个订单，这个handle方法不是单线程处理，或者有多个实例，又刚好这2个请求被同时处理，
            int row = customerRepository.charge(msg.getCustomerId(), msg.getAmount());
            if (row == 0){
                // 更新余额失败
                LOG.info("updata_deposit_error order:{}", msg.getId());
                msg.setStatus("updata_deposit_error");
                jmsTemplate.convertAndSend("order:ticket_error", msg);
                throw new RuntimeException("更新余额失败");
            }
        }
//        更新余额成功，发送票转移消息
        msg.setStatus("PAID");
        jmsTemplate.convertAndSend("order:ticket_move", msg);
    }
}
