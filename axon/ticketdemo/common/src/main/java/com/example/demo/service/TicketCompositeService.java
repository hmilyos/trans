package com.example.demo.service;


import com.example.demo.dto.TicketDTO;

import java.util.List;

public interface TicketCompositeService {

    List<TicketDTO> getMyTickets(Long customerId);

}
