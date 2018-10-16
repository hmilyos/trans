package com.hmily.trans.ticket.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")	
public class TicketController {
	@PostConstruct
	public void init() {
		
	}
}
