package com.syn.tkt.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.config.ApplicationProperties;
import com.syn.tkt.domain.Agent;
import com.syn.tkt.domain.Alert;
import com.syn.tkt.domain.Contact;
import com.syn.tkt.domain.Ticket;
import com.syn.tkt.domain.TicketHistory;
import com.syn.tkt.domain.TicketUIObject;
import com.syn.tkt.repository.AgentRepository;
import com.syn.tkt.repository.ContactRepository;
import com.syn.tkt.repository.TicketHistoryRepository;
import com.syn.tkt.repository.TicketRepository;

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
	
	@Autowired
	RestTemplate restTemplate;

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
	public ResponseEntity<Ticket> createTicket(@RequestParam String type,@RequestParam String subject,@RequestParam String priority,@RequestParam String description,@RequestParam String tag,@RequestParam String assignedToUserType,@RequestParam String requesterUserType,@RequestParam Long requesterId,@RequestParam Long assignedToId,@RequestParam String associatedEntityName,@RequestParam String associatedEntityId,@RequestParam String alertName) throws URISyntaxException {

		ApplicationProperties applicationProperties=ServicedeskApp.getBean(ApplicationProperties.class );
		logger.info("Begin saving ticket ");
		Ticket ticket = saveTicket(type, subject, priority, description, tag, assignedToUserType, requesterUserType, 
				requesterId, assignedToId, associatedEntityName, associatedEntityId, alertName);
		logger.info("End saving ticket ");
		
		logger.info("Begin saving ticket history");
		saveTicketHistory(ticket);
		logger.info("End saving ticket history");
		
		if(ticket.getAssociatedEntityName().equalsIgnoreCase("alert")) {
			logger.info("Begin alert activity push to kafka");
			try {
				sendAlertActivity(alertName, applicationProperties, ticket);
			}catch (Exception e) {
				logger.error("Exception in sending alert activity to kafka : ",e);
			}
			logger.info("End alert activity push to kafka");
		}
		
		return ResponseEntity
				.created(new URI("/api/tickets/" + ticket.getId())).headers(HeaderUtil
				.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ticket.getId().toString()))
				.body(ticket);
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
			Contact requester=contactRepository.findById(requesterId).get();
			String requesterName = requester.getUserName();
			String requesterCompanyName=requester.getCompany().getCompanyName();
			Long assignedToId = ticket.getAssignedToId();
			String assignedToName = null;
			String assignedToCompanyName=null;
			try {
			if (ticket.getAssignedToUserType() != null && ticket.getAssignedToId() != null) {
				if (ticket.getAssignedToUserType().equals("agent")) {
					Agent agent=agentRepository.findById(assignedToId).get();
					assignedToName = agent.getName();
					assignedToCompanyName=agent.getCompany().getCompanyName();
				} else if (ticket.getAssignedToUserType().equals("contact")) {
					Contact contact=contactRepository.findById(assignedToId).get();
					assignedToName = contact.getUserName();
					assignedToCompanyName=contact.getCompany().getCompanyName();
				}
			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if (pageType.equalsIgnoreCase("all")) {
				TicketUIObject obj = new TicketUIObject();
				obj.setId(ticket.getId());
				obj.setRequesterName(requesterName);
				obj.setStatus(ticket.getStatus());
				obj.setAssignedToName(assignedToName);
				obj.setPriority(ticket.getPriority());
				obj.setSubject(ticket.getSubject());
				LocalDate createLocalDate = ticket.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
				obj.setCreateDate(createLocalDate);
				obj.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
				obj.setType(ticket.getType());
				obj.setTags(ticket.getTag());
				obj.setRequesterCompanyName(requesterCompanyName);
				obj.setAssignedToCompanyName(assignedToCompanyName);
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
					LocalDate createLocalDate = ticket.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
					obj.setCreateDate(createLocalDate);
					obj.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
					obj.setType(ticket.getType());
					obj.setTags(ticket.getTag());
					obj.setRequesterCompanyName(requesterCompanyName);
					obj.setAssignedToCompanyName(assignedToCompanyName);
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
					LocalDate createLocalDate = ticket.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
					obj.setCreateDate(createLocalDate);
					obj.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
					obj.setType(ticket.getType());
					obj.setTags(ticket.getTag());
					obj.setRequesterCompanyName(requesterCompanyName);
					obj.setAssignedToCompanyName(assignedToCompanyName);
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
					LocalDate createLocalDate = ticket.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
					obj.setCreateDate(createLocalDate);
					obj.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
					obj.setType(ticket.getType());
					obj.setTags(ticket.getTag());
					obj.setRequesterCompanyName(requesterCompanyName);
					obj.setAssignedToCompanyName(assignedToCompanyName);
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
					LocalDate createLocalDate = ticket.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
					obj.setCreateDate(createLocalDate);
					obj.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
					obj.setType(ticket.getType());
					obj.setTags(ticket.getTag());
					obj.setRequesterCompanyName(requesterCompanyName);
					obj.setAssignedToCompanyName(assignedToCompanyName);
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
					LocalDate createLocalDate = ticket.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
					obj.setCreateDate(createLocalDate);
					obj.setExpectedDateOfCompletion(ticket.getExpectedDateOfCompletion());
					obj.setType(ticket.getType());
					obj.setTags(ticket.getTag());
					obj.setRequesterCompanyName(requesterCompanyName);
					obj.setAssignedToCompanyName(assignedToCompanyName);
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
	public List<Map<String, Object>> alertTicketsByGuid(@PathVariable String guid) {
		Ticket ticket = new Ticket();
		ticket.setAssociatedEntityId(guid);
		ticket.setAssociatedEntityName("alert");
		List<Ticket> tickets = ticketRepository.findAll(Example.of(ticket));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Ticket ticket2 : tickets) {
			Long assignedToId = ticket2.getAssignedToId();
			String assignedToName = null;
			if (ticket2.getAssignedToUserType() != null && ticket2.getAssignedToId() != null) {
				if (ticket2.getAssignedToUserType().equals("agent")) {
					assignedToName = agentRepository.findById(assignedToId).get().getName();
				} else if (ticket2.getAssignedToUserType().equals("contact")) {
					assignedToName = contactRepository.findById(assignedToId).get().getUserName();
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ticket2.getId());
			map.put("subject", ticket2.getSubject());
			map.put("priority", ticket2.getPriority());
			map.put("createdAt", ticket2.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDateTime());
			map.put("assignedToName", assignedToName);
			list.add(map);
		}
		return list;

	}

	@GetMapping("/reportQuicStatistics")
	public Map<String, Object> reportQuicStatistics() {
		List<Ticket> tickets = ticketRepository.findAll();
		long totalTickets = 0;
		long numberOfOpenTicket = 0;
		long numberOfUnresolvedTicket = 0;
		long numberOfClosedTicket = 0;
		for (Ticket ticket : tickets) {
			if (ticket.getStatus().equals("Open")) {
				numberOfOpenTicket++;
			}
			if (!ticket.getStatus().equals("Closed")) {
				numberOfUnresolvedTicket++;
			}
			if (ticket.getStatus().equalsIgnoreCase("Closed")) {
				numberOfClosedTicket++;
			}
			totalTickets++;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalTickets", totalTickets);
		map.put("opentTicketsPercentage", (numberOfOpenTicket * 100) / totalTickets);
		map.put("unresolvedTicketsPercentage", (numberOfUnresolvedTicket * 100) / totalTickets);
		map.put("closedTicktsPercentage", (numberOfClosedTicket * 100) / totalTickets);
		return map;
	}

	@GetMapping("/getBarGraphStatData")
	public Map<String, List<Object>> getGraphStat() {
		List<Ticket> tickets = ticketRepository.findAll();
		List<Object> daysList = new ArrayList<Object>();
		List<Object> numberOfTicketsList = new ArrayList<Object>();
		List<Ticket> last40daysData = new ArrayList<Ticket>();
		for (Ticket ticket : tickets) {
			if (ticket.getCreatedOn().isAfter(Instant.now().minus(40, ChronoUnit.DAYS))) {
				last40daysData.add(ticket);
			}
		}

		for (int i = 1; i <= 40; i++) {
			int numberOfTicketOnTheDay = 0;
			for (Ticket ticket : last40daysData) {
				Instant createInstant = ticket.getCreatedOn();
				Instant matchInstant = Instant.now().minus(40 - i, ChronoUnit.DAYS);
				LocalDate createLocalDate = createInstant.atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate matchLocalDate = matchInstant.atZone(ZoneId.systemDefault()).toLocalDate();
				if (createLocalDate.equals(matchLocalDate)) {
					numberOfTicketOnTheDay++;
				}
			}
			Instant instant = Instant.now().minus(40 - i, ChronoUnit.DAYS);
			daysList.add(instant.atZone(ZoneId.systemDefault()).getDayOfMonth());
			numberOfTicketsList.add(numberOfTicketOnTheDay);
		}
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		map.put("daysList", daysList);
		map.put("numberOfTicketsList", numberOfTicketsList);
		return map;

	}

	@GetMapping("/getTodaysTicketTrendsData")
	public Map<String, List<Object>> getTodaysTicketTrendsData() {
		List<Ticket> tickets = ticketRepository.findAll();
		List<Object> hoursList = new ArrayList<Object>();
		List<Object> numberOfTicketsList = new ArrayList<Object>();
		List<Ticket> last24HoursData = new ArrayList<Ticket>();
		for (Ticket ticket : tickets) {
			if (ticket.getCreatedOn().isAfter(Instant.now().minus(24, ChronoUnit.HOURS))) {
				last24HoursData.add(ticket);
			}
		}

		for (int i = 1; i <= 24; i++) {
			int numberOfTicketOnTheHour = 0;
			for (Ticket ticket : last24HoursData) {
				Instant createInstant = ticket.getCreatedOn();
				Instant matchInstant = Instant.now().minus(24 - i, ChronoUnit.HOURS);
				int createHour = createInstant.atZone(ZoneId.systemDefault()).getHour();
				int matchHour = matchInstant.atZone(ZoneId.systemDefault()).getHour();
				if (createHour==matchHour) {
					numberOfTicketOnTheHour++;
				}
			}
			Instant instant = Instant.now().minus(24 - i, ChronoUnit.HOURS);
			hoursList.add(instant.atZone(ZoneId.systemDefault()).getHour());
			numberOfTicketsList.add(numberOfTicketOnTheHour);
		}
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		map.put("hoursList", hoursList);
		map.put("numberOfTicketsList", numberOfTicketsList);
		return map;
	}
	
	private Ticket saveTicket(String type, String subject, String priority, String description, 
			String tag, String assignedToUserType, String requesterUserType, Long requesterId, Long assignedToId,
			String associatedEntityName, String associatedEntityId, String alertName) {
		Ticket ticket=new Ticket();
		ticket.setType(type);
		ticket.setSubject(subject);
		ticket.setDescription(description);
		ticket.setPriority(priority);
		ticket.setTag(tag);
		ticket.setAssignedToUserType(assignedToUserType);
		ticket.setRequesterUserType(requesterUserType);
		ticket.setRequesterId(requesterId);
		ticket.setAssignedToUserType(assignedToUserType);
		ticket.setAssignedToId(assignedToId);
		ticket.setAssociatedEntityName(associatedEntityName);
		ticket.setAssociatedEntityId(associatedEntityId);
		LocalDate date = LocalDate.now();
		ticket.setCreatedOn(Instant.now());
		ticket.expectedDateOfCompletion(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()).plusDays(10));
		ticket.setStatus("Open");
		ticket = ticketRepository.save(ticket);
		logger.debug("Ticket saved successfully: "+ticket.toString());
		return ticket;
	}
	
	private TicketHistory saveTicketHistory(Ticket ticket) {
		TicketHistory ticketHistory = new TicketHistory();
		BeanUtils.copyProperties(ticket, ticketHistory);
		ticketHistory.setOperationType("insert");
		ticketHistory.setTicket(ticket);
		ticketHistory = ticketHistoryRepository.save(ticketHistory);
		logger.debug("TicketHistory saved successfully : " + ticketHistory.toString());
		return ticketHistory;
	}

	
	private void sendAlertActivity(String alertName, ApplicationProperties applicationProperties, Ticket ticket) throws JSONException {
		String url = applicationProperties.getAlertSrvUrl()+"/api/getAlert/"+ticket.getAssociatedEntityId();
		Alert alert = restTemplate.getForObject(url, Alert.class);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("guid", ticket.getAssociatedEntityId());
		jsonObject.put("name", alertName);
		jsonObject.put("action","New ticket created");
		jsonObject.put("action_description", "New ticket created. Ticket Id - "+ticket.getId());
		jsonObject.put("created_on", alert.getCreatedOn().toEpochMilli());
		jsonObject.put("updated_on", Instant.now().toEpochMilli());
		jsonObject.put("alert_state", alert.getAlertState());
		jsonObject.put("ticket_id", ticket.getId());
		jsonObject.put("ticket_name", ticket.getSubject());
		jsonObject.put("ticket_url", "");
		jsonObject.put("ticket_description", "New ticket created for alert - "+alertName+"");
		jsonObject.put("user_name", "Automated");
		jsonObject.put("event_type", "update");
		jsonObject.put("change_log", "");
		jsonObject.put("fired_time", Instant.now().toEpochMilli());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString(applicationProperties.getKafkaSendDataUrl())
				.queryParam("topic", applicationProperties.getAlertActivityKafaTopic()).queryParam("msg", jsonObject.toString());
		restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
		logger.debug("Alert activity sent to kafka topic  "+applicationProperties.getAlertActivityKafaTopic()+". Alert activity: "+jsonObject);
	}
}
