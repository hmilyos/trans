package com.hmily.transaxon.springaxonsaga.order.query;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderEntityRepository extends JpaRepository<OrderEntity, String> {
}
