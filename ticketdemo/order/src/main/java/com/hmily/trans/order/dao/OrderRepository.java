package com.hmily.trans.order.dao;

import com.hmily.trans.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);

    Order findOneByUuid(String uuid);

    List<Order> findAllByStatusAndCreatedDateBefore(String status, ZonedDateTime checkTime);
}
