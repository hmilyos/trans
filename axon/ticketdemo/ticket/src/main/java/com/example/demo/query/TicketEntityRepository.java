package com.example.demo.query;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketEntityRepository extends JpaRepository<TicketEntity, String> {
}
