package com.example.demo.query;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
}
