package com.hmily.trans.common.service;

import com.hmily.trans.common.dto.OrderDTO;

import java.util.List;

public interface IOrderCompositeService {

    List<OrderDTO> getMyOrder(Long id);
}
