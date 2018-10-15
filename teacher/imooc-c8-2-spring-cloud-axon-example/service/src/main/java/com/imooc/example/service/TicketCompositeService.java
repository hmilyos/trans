package com.imooc.example.service;

import com.imooc.example.dto.TicketDTO;

import java.util.List;

/**
 * Created by mavlarn on 2018/4/5.
 */
public interface TicketCompositeService {

    List<TicketDTO> getMyTickets(Long customerId);

}
