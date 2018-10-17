package com.hmily.trans.order.service;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.service.IOrderCompositeService;
import com.hmily.trans.order.domain.Order;

import java.util.List;

public interface IOrderService extends IOrderCompositeService {
    void create(OrderDTO dto);

    List<OrderDTO> getMyOrder(Long customerId);

    List<Order> getAll();
}
