package com.hmily.trans.user.dao;

import com.hmily.trans.user.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);

    @Modifying
    @Query("UPDATE customer SET deposit = deposit - ?2 WHERE id = ?1 and deposit > ?2")
    int charge(Long customerId, int amount);
}
