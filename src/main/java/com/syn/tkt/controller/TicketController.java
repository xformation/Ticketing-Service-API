package com.syn.tkt.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syn.tkt.domain.Ticket;
import com.syn.tkt.domain.TicketUIObject;
import com.syn.tkt.repository.TicketRepository;
import com.syn.tkt.service.dto.TicketDTO;
import com.syn.tkt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class TicketController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ENTITY_NAME = "ticket";
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/addTicket")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) throws URISyntaxException {
    	logger.debug("REST request to save Ticket : {}", ticket);
        if (ticket.getId() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalDate date=LocalDate.now();
//        ticket.createdOn(date);
        ticket.expectedDateOfCompletion(LocalDate.of(date.getYear(), date.getMonth(),date.getDayOfMonth()+10));
        ticket.setStatus("Open");
        Ticket result = ticketRepository.save(ticket);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/listAllTickets")
    public List<Ticket> listAllTickets(){
    	logger.info("Request to get list of all tickets");
    	return ticketRepository.findAll();
    }
//    @GetMapping("/GetTicketForUI")
//    public List<TicketUIObject> getTicketForUI(){
//    	List<Ticket> tickets=ticketRepository.findAll(Sort.by(Direction.DESC, "id"));
//    	List<TicketUIObject> list=new ArrayList<TicketUIObject>();
//    	for(Ticket ticket : tickets) {
////    		Long contactId=Long.parseLong(ticket.get)
//    		
//    	}
//		return null;
//    	
//    }
    
}
