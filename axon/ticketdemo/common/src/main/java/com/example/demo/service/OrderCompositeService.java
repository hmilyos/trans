package com.example.demo.service;


import com.example.demo.dto.OrderDTO;

import java.util.List;


public interface OrderCompositeService {

    List<OrderDTO> getMyOrder(Long id);
}
