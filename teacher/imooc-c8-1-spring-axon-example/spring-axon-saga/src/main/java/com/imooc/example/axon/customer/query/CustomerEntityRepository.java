package com.imooc.example.axon.customer.query;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/5/22.
 */
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
}
