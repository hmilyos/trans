package com.imooc.example.service;

import com.imooc.example.dto.OrderDTO;

import java.util.List;

/**
 * Created by mavlarn on 2018/2/14.
 */
public interface OrderCompositeService {

    List<OrderDTO> getMyOrder(Long id);
}
