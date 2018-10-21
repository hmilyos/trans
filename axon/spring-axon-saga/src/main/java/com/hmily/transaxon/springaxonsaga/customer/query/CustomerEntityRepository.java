package com.hmily.transaxon.springaxonsaga.customer.query;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
}
