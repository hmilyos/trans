package com.hmily.trans.order.service.impl;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.order.dao.OrderRepository;
import com.hmily.trans.order.domain.Order;
import com.hmily.trans.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    private TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator();

//    接收 锁票成功的消息
    @Transactional
    @JmsListener(destination = "order:locked", containerFactory = "msgFactory")
    public void handle(OrderDTO msg) {
        LOG.info("Get new order to create:{}", msg);
        // 通过保存到数据库，来使用uuid处理重复消息
        if (orderRepository.findOneByUuid(msg.getUuid()) != null) {
            LOG.info("Msg already processed:{}", msg);
        } else {
            Order order = newOrder(msg);
            orderRepository.save(order);
            msg.setId(order.getId());
        }
        msg.setStatus("NEW");
//        订单创建成功，通知去支付
        jmsTemplate.convertAndSend("order:pay", msg);
    }

    private Order newOrder(OrderDTO dto) {
        Order order = new Order();
        order.setUuid(dto.getUuid());
        order.setAmount(dto.getAmount());
        order.setTitle(dto.getTitle());
        order.setCustomerId(dto.getCustomerId());
        order.setTicketNum(dto.getTicketNum());
        order.setStatus("NEW");
        order.setCreatedDate(ZonedDateTime.now());
        return order;
    }

//    订单完成
    @Transactional
    @JmsListener(destination = "order:finish", containerFactory = "msgFactory")
    public void handleFinish(OrderDTO msg) {
        LOG.info("Get finished order:{}", msg);
        Order order = orderRepository.findOne(msg.getId());
        order.setStatus("FINISH");
        orderRepository.save(order);
    }

    /**
     * 接收订单失败的消息
     * 订单失败的几种情况：
     * 1. 一开始索票失败。
     * 2. 扣费失败后，解锁票，然后出发
     * 3. 定时任务检测到订单超时
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "order:fail", containerFactory = "msgFactory")
    public void handleFailed(OrderDTO msg) {
        LOG.info("Get failed order:{}", msg);
        Order order;
        if (msg.getId() == null) {
            order = newOrder(msg);
            order.setReason("TICKET_LOCK_FAIL"); // 锁票失败，可能票id不对，或者已被别人买走
        } else {
            order = orderRepository.findOne(msg.getId());
            if (msg.getStatus().equals("NOT_ENOUGH_DEPOSIT")) {
                order.setReason("NOT_ENOUGH_DEPOSIT");
            } else if (msg.getStatus().equals("updata_deposit_error")){
                order.setReason("updata_deposit_error");
            }
        }
        order.setStatus("FAIL");
        orderRepository.save(order);
    }

    @Override
    public void create(OrderDTO dto) {
//        对购票信息添加一个唯一 id 用于做幂等性判断
//        然后就丢到消息队列里面，流转到下一步 锁票操作
        dto.setUuid(uuidGenerator.generate().toString());
        jmsTemplate.convertAndSend("order:new", dto);
    }

    @Override
    public List<OrderDTO> getMyOrder(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        return orders.stream().map(
                order -> {
                    OrderDTO dto = new OrderDTO();
                    dto.setId(order.getId());
                    dto.setStatus(order.getStatus());
                    dto.setTicketNum(order.getTicketNum());
                    dto.setAmount(order.getAmount());
                    dto.setCustomerId(order.getCustomerId());
                    dto.setTitle(order.getTitle());
                    dto.setUuid(order.getUuid());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
