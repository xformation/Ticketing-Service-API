package com.syn.tkt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syn.tkt.domain.Ticket;
import com.syn.tkt.repository.TicketRepository;

@RestController
@RequestMapping("/api")
public class TicketController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ENTITY_NAME = "ticket";
    
    private TicketRepository ticketRepository;
    
    @GetMapping("/listAllTickets")
    public List<Ticket> listAllTickets(){
    	logger.info("Request to get list of all tickets");
    	return ticketRepository.findAll();
    }

    
}
