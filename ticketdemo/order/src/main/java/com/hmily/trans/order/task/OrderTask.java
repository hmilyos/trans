package com.hmily.trans.order.task;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.order.dao.OrderRepository;
import com.hmily.trans.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderTask {
    private static final Logger LOG = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JmsTemplate jmsTemplate;


    @Scheduled(fixedDelay = 10000L)
    public void checkInvalidOrder() {
        ZonedDateTime checkTime = ZonedDateTime.now().minusMinutes(1L);
        List<Order> orders = orderRepository.findAllByStatusAndCreatedDateBefore("NEW", checkTime);
        orders.stream().forEach(
                order -> {
                    LOG.error("Order timeout:{}", order);
                    OrderDTO dto = new OrderDTO();
                    dto.setId(order.getId());
                    dto.setTicketNum(order.getTicketNum());
                    dto.setUuid(order.getUuid());
                    dto.setAmount(order.getAmount());
                    dto.setTitle(order.getTitle());
                    dto.setCustomerId(order.getCustomerId());
                    dto.setStatus("TIMEOUT");
                    jmsTemplate.convertAndSend("order:ticket_error", dto);
                }
        );
    }
}
