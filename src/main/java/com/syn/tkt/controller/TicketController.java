package com.syn.tkt.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syn.tkt.domain.Ticket;
import com.syn.tkt.domain.TicketHistory;
import com.syn.tkt.domain.TicketUIObject;
import com.syn.tkt.repository.AgentRepository;
import com.syn.tkt.repository.ContactRepository;
import com.syn.tkt.repository.TicketHistoryRepository;
import com.syn.tkt.repository.TicketRepository;
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

	@Autowired
	private TicketHistoryRepository ticketHistoryRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private AgentRepository agentRepository;

	/**
	 * {@code POST  /tickets} : Create a new ticket.
	 *
	 * @param ticketDTO the ticketDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new ticketDTO, or with status {@code 400 (Bad Request)} if
	 *         the ticket has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/addTicket")
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) throws URISyntaxException {
		logger.debug("REST request to save Ticket : {}", ticket);
		if (ticket.getId() != null) {
			throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
		}
		LocalDate date = LocalDate.now();
		ticket.setCreatedOn(Instant.now());
		ticket.expectedDateOfCompletion(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth() + 10));
		ticket.setStatus("Open");
		Ticket result = ticketRepository.save(ticket);
		TicketHistory ticketHistory = new TicketHistory();
		ticketHistory.setSubject(ticket.getSubject());
		ticketHistory.setDescription(ticket.getDescription());
		ticketHistory.setType(ticket.getType());
		ticketHistory.setStatus(ticket.getStatus());
		ticketHistory.setPriority(ticket.getPriority());
		ticketHistory.setCreatedOn(ticket.getCreatedOn());
		ticketHistory.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
		ticketHistory.setTag(ticket.getTag());
		ticketHistory.setAssignedToUserType(ticket.getAssignedToUserType());
		ticketHistory.setAssignedToId(ticket.getAssignedToId());
		ticketHistory.setRequesterUserType(ticket.getRequesterUserType());
		ticketHistory.setRequesterId(ticket.getRequesterId());
		ticketHistory.setOperationType("insert");
		logger.debug(" save TicketHistory : {}", ticketHistory);
		ticketHistory.setTicket(ticket);
		ticketHistoryRepository.save(ticketHistory);
		return ResponseEntity
				.created(new URI("/api/tickets/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/listAllTickets")
	public List<Ticket> listAllTickets() {
		logger.info("Request to get list of all tickets");
		return ticketRepository.findAll();
	}

	@GetMapping("/getTicketForTable")
	public List<TicketUIObject> getTicketForUI(@RequestParam String pageType) {
		List<Ticket> tickets = ticketRepository.findAll(Sort.by(Direction.DESC, "id"));
		List<TicketUIObject> list = new ArrayList<TicketUIObject>();
		for (Ticket ticket : tickets) {
			Long requesterId = ticket.getRequesterId();
			String requesterName = contactRepository.findById(requesterId).get().getUserName();
			Long assignedToId = ticket.getAssignedToId();
			String assignedToName = null;
			if (ticket.getAssignedToUserType() != null && ticket.getAssignedToId() != null) {
				if (ticket.getAssignedToUserType().equals("agent")) {
					assignedToName = agentRepository.findById(assignedToId).get().getName();
				} else if (ticket.getAssignedToUserType().equals("contact")) {
					assignedToName = contactRepository.findById(assignedToId).get().getUserName();
				}
			}
			if (pageType.equalsIgnoreCase("all")) {
				TicketUIObject obj = new TicketUIObject();
				obj.setId(ticket.getId());
				obj.setRequesterName(requesterName);
				obj.setStatus(ticket.getStatus());
				obj.setAssignedToName(assignedToName);
				obj.setPriority(ticket.getPriority());
				obj.setSubject(ticket.getSubject());
				LocalDateTime datetime = LocalDateTime.ofInstant(ticket.getCreatedOn(), ZoneOffset.UTC);
				String formattedCreateDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(datetime);
				obj.setCreateDate(formattedCreateDate);
				list.add(obj);
			} else if (pageType.equalsIgnoreCase("Open Tickets")) {
				if (ticket.getStatus().equals("Open")) {
					TicketUIObject obj = new TicketUIObject();
					obj.setId(ticket.getId());
					obj.setRequesterName(requesterName);
					obj.setStatus(ticket.getStatus());
					obj.setAssignedToName(assignedToName);
					obj.setPriority(ticket.getPriority());
					obj.setSubject(ticket.getSubject());
					LocalDateTime datetime = LocalDateTime.ofInstant(ticket.getCreatedOn(), ZoneOffset.UTC);
					String formattedCreateDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(datetime);
					obj.setCreateDate(formattedCreateDate);
					list.add(obj);
				}
			} else if (pageType.equalsIgnoreCase("Due Today")) {
				if (ticket.getExpectedDateOfCompletion().isEqual(LocalDate.now())
						&& !ticket.getStatus().equalsIgnoreCase("Closed")) {
					TicketUIObject obj = new TicketUIObject();
					obj.setId(ticket.getId());
					obj.setRequesterName(requesterName);
					obj.setStatus(ticket.getStatus());
					obj.setAssignedToName(assignedToName);
					obj.setPriority(ticket.getPriority());
					obj.setSubject(ticket.getSubject());
					LocalDateTime datetime = LocalDateTime.ofInstant(ticket.getCreatedOn(), ZoneOffset.UTC);
					String formattedCreateDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(datetime);
					obj.setCreateDate(formattedCreateDate);
					list.add(obj);
				}
			} else if (pageType.equalsIgnoreCase("Unassigned") && !ticket.getStatus().equalsIgnoreCase("Closed")) {
				if (ticket.getAssignedToId() == null && ticket.getAssignedToUserType() == null) {
					TicketUIObject obj = new TicketUIObject();
					obj.setId(ticket.getId());
					obj.setRequesterName(requesterName);
					obj.setStatus(ticket.getStatus());
					obj.setAssignedToName(assignedToName);
					obj.setPriority(ticket.getPriority());
					obj.setSubject(ticket.getSubject());
					LocalDateTime datetime = LocalDateTime.ofInstant(ticket.getCreatedOn(), ZoneOffset.UTC);
					String formattedCreateDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(datetime);
					obj.setCreateDate(formattedCreateDate);
					list.add(obj);
				}
			} else if (pageType.equalsIgnoreCase("Unresolved") && !ticket.getStatus().equalsIgnoreCase("Closed")) {
				if (!ticket.getStatus().equals("Closed")) {
					TicketUIObject obj = new TicketUIObject();
					obj.setId(ticket.getId());
					obj.setRequesterName(requesterName);
					obj.setStatus(ticket.getStatus());
					obj.setAssignedToName(assignedToName);
					obj.setPriority(ticket.getPriority());
					obj.setSubject(ticket.getSubject());
					LocalDateTime datetime = LocalDateTime.ofInstant(ticket.getCreatedOn(), ZoneOffset.UTC);
					String formattedCreateDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(datetime);
					obj.setCreateDate(formattedCreateDate);
					list.add(obj);
				}
			} else if (pageType.equalsIgnoreCase("Overdue") && !ticket.getStatus().equalsIgnoreCase("Closed")) {
				if (ticket.getExpectedDateOfCompletion().isBefore((LocalDate.now()))) {
					TicketUIObject obj = new TicketUIObject();
					obj.setId(ticket.getId());
					obj.setRequesterName(requesterName);
					obj.setStatus(ticket.getStatus());
					obj.setAssignedToName(assignedToName);
					obj.setPriority(ticket.getPriority());
					obj.setSubject(ticket.getSubject());
					LocalDateTime datetime = LocalDateTime.ofInstant(ticket.getCreatedOn(), ZoneOffset.UTC);
					String formattedCreateDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(datetime);
					obj.setCreateDate(formattedCreateDate);
					list.add(obj);
				}
			}

		}
		return list;
	}

	@GetMapping("/getTicketingData")
	public List<Map<String, String>> getTicketingData() {
		List<Ticket> tickets = ticketRepository.findAll();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		long numberOfOpenTicket = 0;
		long numberOfDueTodayTicket = 0;
		long numberofUnassignedTicket = 0;
		long numberOfUnresolvedTicket = 0;
		long numberOfOverdueTicket = 0;
		for (Ticket ticket : tickets) {
			if (ticket.getStatus().equals("Open")) {
				numberOfOpenTicket++;
			}
			if (ticket.getExpectedDateOfCompletion().isEqual(LocalDate.now())
					&& !ticket.getStatus().equalsIgnoreCase("Closed")) {
				numberOfDueTodayTicket++;
			}
			if (ticket.getAssignedToId() == null && ticket.getAssignedToUserType() == null
					&& !ticket.getStatus().equalsIgnoreCase("Closed")) {
				numberofUnassignedTicket++;
			}
			if (!ticket.getStatus().equals("Closed")) {
				numberOfUnresolvedTicket++;
			}
			if (ticket.getExpectedDateOfCompletion().isBefore((LocalDate.now()))
					&& !ticket.getStatus().equalsIgnoreCase("Closed")) {
				numberOfOverdueTicket++;
			}
		}
		Map<String, String> openTicketMap = new HashMap<String, String>();
		openTicketMap.put("ticketingNumber", String.valueOf(numberOfOpenTicket));
		openTicketMap.put("ticketingname", "Open Tickets");
		list.add(openTicketMap);
		Map<String, String> dueTodayMap = new HashMap<String, String>();
		dueTodayMap.put("ticketingNumber", String.valueOf(numberOfDueTodayTicket));
		dueTodayMap.put("ticketingname", "Due Today");
		list.add(dueTodayMap);
		Map<String, String> unassignedMap = new HashMap<String, String>();
		unassignedMap.put("ticketingNumber", String.valueOf(numberofUnassignedTicket));
		unassignedMap.put("ticketingname", "Unassigned");
		list.add(unassignedMap);
		Map<String, String> unresolvedMap = new HashMap<String, String>();
		unresolvedMap.put("ticketingNumber", String.valueOf(numberOfUnresolvedTicket));
		unresolvedMap.put("ticketingname", "Unresolved");
		list.add(unresolvedMap);
		Map<String, String> overDueMap = new HashMap<String, String>();
		overDueMap.put("ticketingNumber", String.valueOf(numberOfOverdueTicket));
		overDueMap.put("ticketingname", "Overdue");
		list.add(overDueMap);
		return list;
	}
	
	@GetMapping("/alertTicketsByGuid/{guid}")
	public List<Map<String, Object>> alertTicketsByGuid(@PathVariable String guid){
		Ticket ticket=new Ticket();
		ticket.setAssociatedEntityId(guid);
		ticket.setAssociatedEntityName("alert");
		List<Ticket> tickets=ticketRepository.findAll(Example.of(ticket));
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		for(Ticket ticket2 : tickets) {
			Long assignedToId = ticket.getAssignedToId();
			String assignedToName = null;
			if (ticket.getAssignedToUserType() != null && ticket.getAssignedToId() != null) {
				if (ticket.getAssignedToUserType().equals("agent")) {
					assignedToName = agentRepository.findById(assignedToId).get().getName();
				} else if (ticket.getAssignedToUserType().equals("contact")) {
					assignedToName = contactRepository.findById(assignedToId).get().getUserName();
				}
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", ticket2.getId());
			map.put("subject", ticket2.getSubject());
			map.put("priority", ticket2.getPriority());
			map.put("createdAt", ticket2.getCreatedOn());
			map.put("assignedToName", assignedToName);
			list.add(map);
		}
		return list;
		
	}

}
