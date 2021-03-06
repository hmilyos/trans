package com.hmily.trans.ticket.controller;

import javax.annotation.PostConstruct;

import com.hmily.trans.common.dto.OrderDTO;
import com.hmily.trans.common.dto.TicketDTO;
import com.hmily.trans.ticket.domain.Ticket;
import com.hmily.trans.ticket.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")	
public class TicketController {
    @Autowired
    private ITicketService ticketService;
	@PostConstruct
	public void init() {
        ticketService.init();
	}

	@PostMapping("")
    public OrderDTO create(@RequestBody OrderDTO dto){
	    return dto;
    }

    @GetMapping("/{customerId}")
    public List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId){
        return ticketService.getMyTickets(customerId);
    }

    @GetMapping("")
    public List<Ticket> getAll() {
        return ticketService.getAll();
    }

//    测试锁票方法 1
    @PostMapping("/lock")
    public Ticket lock(@RequestBody OrderDTO orderDTO){
        return ticketService.lockTicket(orderDTO);
    }
//    测试锁票方法 2
    @PostMapping("/lock2")
    public int lock2(@RequestBody OrderDTO orderDTO){
        return ticketService.lockTicket2(orderDTO);
    }

}
