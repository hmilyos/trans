package com.hmily.trans.ticket.service;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.service.ITicketCompositeService;
import com.hmily.trans.ticket.domain.Ticket;

import java.util.List;

public interface ITicketService extends ITicketCompositeService {

    void init();

    List<Ticket> getAll();

    Ticket lockTicket(OrderDTO orderDTO);

    int lockTicket2(OrderDTO orderDTO);
}
