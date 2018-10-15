package com.imooc.example.order;

import com.imooc.example.order.query.OrderEntity;
import com.imooc.example.order.query.OrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mavlarn on 2018/5/28.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @GetMapping("")
    public List<OrderEntity> get() {
        return orderEntityRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public OrderEntity get(@PathVariable String orderId) {
        return orderEntityRepository.findOne(orderId);
    }
}
