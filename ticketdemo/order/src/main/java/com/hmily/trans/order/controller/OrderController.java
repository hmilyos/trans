package com.hmily.trans.order.controller;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.order.domain.Order;
import com.hmily.trans.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Transactional
    @PostMapping("")
    public void create(@RequestBody OrderDTO dto) {
        orderService.create(dto);
    }
    @GetMapping("/{customerId}")
    public List<OrderDTO> getMyOrder(@PathVariable Long customerId) {
        return orderService.getMyOrder(customerId);
    }

    @GetMapping("")
    public List<Order> getAll() {
        return orderService.getAll();
    }

}
