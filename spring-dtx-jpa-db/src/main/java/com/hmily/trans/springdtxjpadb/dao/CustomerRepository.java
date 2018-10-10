package com.hmily.trans.springdtxjpadb.dao;

import com.hmily.trans.springdtxjpadb.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

        Customer findOneByUsername(String username);
}