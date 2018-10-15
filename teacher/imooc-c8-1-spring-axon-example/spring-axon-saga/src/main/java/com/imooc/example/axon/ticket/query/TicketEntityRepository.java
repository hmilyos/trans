package com.imooc.example.axon.ticket.query;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/5/28.
 */
public interface TicketEntityRepository extends JpaRepository<TicketEntity, String> {
}
