package com.hmily.trans.common.service;


import com.hmily.trans.common.dto.OrderDTO;

public interface IOrderService {

    OrderDTO create(OrderDTO dto);

    OrderDTO getMyOrder(Long id);
}
