package com.hmily.trans.springdtxjmsdb.dao;

import com.hmily.trans.springdtxjmsdb.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
