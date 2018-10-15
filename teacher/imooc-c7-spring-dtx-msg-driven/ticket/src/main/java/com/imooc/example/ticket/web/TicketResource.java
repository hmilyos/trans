package com.imooc.example.ticket.web;

import com.imooc.example.dto.OrderDTO;
import com.imooc.example.dto.TicketDTO;
import com.imooc.example.service.TicketCompositeService;
import com.imooc.example.ticket.dao.TicketRepository;
import com.imooc.example.ticket.domain.Ticket;
import com.imooc.example.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/ticket")
public class TicketResource implements TicketCompositeService {

    @PostConstruct
    public void init() {
        if (ticketRepository.count() > 0) {
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setName("No.1");
        ticket.setTicketNum(110L);
        ticketRepository.save(ticket);
    }

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;

    @PostMapping("")
    public OrderDTO create(@RequestBody OrderDTO dto) {
        return dto;
    }

    @GetMapping("/{customerId}")
    public List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId) {
        List<Ticket> tickets = ticketRepository.findAllByOwner(customerId);
        return tickets.stream().map(ticket -> {
            TicketDTO dto = new TicketDTO();
            dto.setTicketNum(ticket.getTicketNum());
            dto.setId(ticket.getId());
            dto.setLockUser(ticket.getLockUser());
            dto.setName(ticket.getName());
            dto.setOwner(ticket.getOwner());
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping("/lock")
    public Ticket lock(@RequestBody OrderDTO dto) {
        return ticketService.lockTicket(dto);
    }
    @PostMapping("/lock2")
    public int lock2(@RequestBody OrderDTO dto) {
        return ticketService.lockTicket2(dto);
    }

    @GetMapping("")
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

}
