package com.hmily.trans.ticket.service.impl;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.dto.TicketDTO;
import com.hmily.trans.ticket.dao.TicketRepository;
import com.hmily.trans.ticket.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmily.trans.ticket.service.ITicketService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class TicketServiceImpl implements ITicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void init() {
        if (ticketRepository.count() > 0){
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setName("No.1");
        ticket.setTicketNum(110L);
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket lockTicket(OrderDTO orderDTO) {
        Ticket ticket = ticketRepository.findOneByTicketNum(orderDTO.getTicketNum());
        ticket.setLockUser(orderDTO.getCustomerId());
        ticket = ticketRepository.save(ticket);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return ticket;
    }

    @Override
    public int lockTicket2(OrderDTO orderDTO) {
        int updateCount = ticketRepository.lockTicket(orderDTO.getCustomerId(), orderDTO.getTicketNum());
        LOG.info("Updated ticket count:{}", updateCount);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return updateCount;
    }


    @Override
    public List<TicketDTO> getMyTickets(Long customerId) {

        List<Ticket> tickets = ticketRepository.findAllByOwner(customerId);
        return tickets.stream().map(
               ticket -> {
                   TicketDTO ticketDTO = new TicketDTO();
                   ticketDTO.setTicketNum(ticket.getTicketNum());
                   ticketDTO.setId(ticket.getId());
                   ticketDTO.setLockUser(ticket.getLockUser());
                   ticketDTO.setName(ticket.getName());
                   ticketDTO.setOwner(ticket.getOwner());
                   return ticketDTO;
               }
        ).collect(Collectors.toList());
    }


}
