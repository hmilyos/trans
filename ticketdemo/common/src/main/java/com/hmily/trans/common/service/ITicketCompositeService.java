package com.hmily.trans.common.service;

import com.hmily.trans.common.dto.TicketDTO;

import java.util.List;

public interface ITicketCompositeService {

    List<TicketDTO> getMyTickets(Long customerId);
}
