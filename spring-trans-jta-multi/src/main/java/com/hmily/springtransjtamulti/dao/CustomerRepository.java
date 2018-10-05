package com.hmily.springtransjtamulti.dao;


import com.hmily.springtransjtamulti.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
