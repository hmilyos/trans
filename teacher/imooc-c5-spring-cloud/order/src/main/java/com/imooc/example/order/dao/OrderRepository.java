package com.imooc.example.order.dao;

import com.imooc.example.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/1/20.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOneByTitle(String title);
}
