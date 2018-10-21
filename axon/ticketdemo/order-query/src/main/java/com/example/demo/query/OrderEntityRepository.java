package com.example.demo.query;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderEntityRepository extends JpaRepository<OrderEntity, String> {
}
