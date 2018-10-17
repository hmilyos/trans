package com.hmily.trans.ticket.dao;

import com.hmily.trans.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOwner(Long owner);


    Ticket findOneByTicketNum(Long num);

    //    @Modifying(clearAutomatically = true)
    @Modifying
    @Query("UPDATE ticket SET lockUser = ?1 WHERE lockUser is NULL and ticketNum = ?2")
    int lockTicket(Long customerId, Long ticketNum);
    
    @Modifying
    @Query("UPDATE ticket SET owner = ?1, lockUser = null WHERE lockUser = ?1 and ticketNum = ?2")
    int moveTicket(Long customerId, Long ticketNum);
    
    @Modifying
    @Query("UPDATE ticket SET owner = NULL WHERE owner = ?1 and ticketNum = ?2")
    int unMoveTicket(Long customerId, Long ticketNum);
    
    @Modifying
    @Query("UPDATE ticket SET lockUser = null WHERE lockUser = ?1 and ticketNum = ?2")
    int unLockTicket(Long customerId, Long ticketNum);
}
