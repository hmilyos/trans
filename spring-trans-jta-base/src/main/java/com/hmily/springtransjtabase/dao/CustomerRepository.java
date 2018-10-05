package com.hmily.springtransjtabase.dao;


import com.hmily.springtransjtabase.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
