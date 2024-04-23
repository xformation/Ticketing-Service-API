











package com.synectiks.tkt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.synectiks.tkt.domain.Agent;
import com.synectiks.tkt.domain.Company;
import com.synectiks.tkt.repository.AgentRepository;
import com.synectiks.tkt.repository.CompanyRepository;
import com.synectiks.tkt.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synectiks.tkt.config.Constants;
import com.synectiks.tkt.domain.Ticket;
import com.synectiks.tkt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Agent}.
 */
@RestController
@RequestMapping("/api")
public class AgentController {

	private final Logger log = LoggerFactory.getLogger(AgentController.class);

	private static final String ENTITY_NAME = "servicedeskAgent";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private TicketRepository ticketRepository;

	/**
	 * {@code POST  /agents} : Create a new agent.
	 *
	 * @param agent the agent to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new agent, or with status {@code 400 (Bad Request)} if the
	 *         agent has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/addAgent2")
	public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) throws URISyntaxException {
		log.debug("REST request to save Agent : {}", agent);
		if (agent.getId() != null) {
			throw new BadRequestAlertException("A new agent cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Agent result = agentRepository.save(agent);
		return ResponseEntity
				.created(new URI("/api/agents/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PostMapping("/addAgent")
	public ResponseEntity<Agent> createContact(@RequestParam(required = false) MultipartFile contactPhoto, @RequestParam String fullName,
			@RequestParam String title, @RequestParam(required = false) String primaryEmail,
			@RequestParam(required = false) String alternateEmail, @RequestParam(required = false) String workPhone,
			@RequestParam(required = false) String mobilePhone, @RequestParam(required = false) String twitterHandle,
			@RequestParam(required = false) String uniqueExternalId, @RequestParam(required = false) Long companyId,
			@RequestParam(required = false) MultipartFile logo, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) String description, @RequestParam(required = false) String notes,
			@RequestParam(required = false) String domain, @RequestParam(required = false) String healthScore,
			@RequestParam(required = false) String accountTier, @RequestParam(required = false) LocalDate renewalDate,
			@RequestParam(required = false) String industry, @RequestParam String agentDescription,
			@RequestParam String address) throws URISyntaxException {
		log.debug("REST request to save Contact : {}", title);
		Agent result = null;
		if (companyId == -1) {
			log.debug("REST request to save Company : {}", companyName);
			File file = new File(Constants.COMPANY_LOGO_FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			Path path = Paths.get(Constants.COMPANY_LOGO_FILE_PATH, logo.getOriginalFilename());
			try {
				Files.write(path, logo.getBytes());
			} catch (IOException e) {
				log.error("IOException while creating company logo file. ", e);
			}

			Company company = new Company();
			company.setCompanyName(companyName);
			company.setDescription(description);
			company.setNotes(notes);
			company.setDomain(domain);
			company.setHealthScore(healthScore);
			company.setAccountTier(accountTier);
			company.setRenewalDate(renewalDate);
			company.setIndustry(industry);
			company.setCompanyLogoFileName(logo.getOriginalFilename());
			company.setCompanyLogoFileLocation(Constants.COMPANY_LOGO_FILE_PATH);
			company = companyRepository.save(company);
			Agent agent = new Agent();
			agent.setName(fullName);
			;
			agent.setTitle(title);
			agent.setPrimaryEmail(primaryEmail);
			agent.setAlternateEmail(alternateEmail);
			agent.setWorkPhone(workPhone);
			agent.setMobilePhone(mobilePhone);
			agent.setTwitterHandle(twitterHandle);
			agent.setUniqueExternalId(uniqueExternalId);
			agent.setCompany(company);
			agent.setImageFileName(contactPhoto.getOriginalFilename());
			agent.setImageLocation(Constants.AGENT_PHOTO_FILE_PATH);
			agent.setDescription(agentDescription);
			agent.setAddress(address);
			agent.createdOn(Instant.now());
			if (agent.getId() != null) {
				throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
			}
			result = agentRepository.save(agent);
		} else {
			File file = new File(Constants.CONTACT_PHOTO_FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			Path path = Paths.get(Constants.CONTACT_PHOTO_FILE_PATH, contactPhoto.getOriginalFilename());
			try {
				Files.write(path, contactPhoto.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Company company = companyRepository.findById(companyId).get();

			Agent agent = new Agent();
			agent.setName(fullName);
			agent.setTitle(title);
			agent.setPrimaryEmail(primaryEmail);
			agent.setAlternateEmail(alternateEmail);
			agent.setWorkPhone(workPhone);
			agent.setMobilePhone(mobilePhone);
			agent.setTwitterHandle(twitterHandle);
			agent.setUniqueExternalId(uniqueExternalId);
			agent.setCompany(company);
			agent.setImageFileName(contactPhoto.getOriginalFilename());
			agent.setImageLocation(Constants.AGENT_PHOTO_FILE_PATH);
			agent.setDescription(agentDescription);
			agent.createdOn(Instant.now());
			agent.setAddress(address);
			if (agent.getId() != null) {
				throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
			}
			result = agentRepository.save(agent);
		}
		return ResponseEntity
				.created(new URI("/api/agents/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/topPerformerAgents")
	public List<Map<String, Object>> topPerformerAgents() {
		List<Agent> agents = agentRepository.findAll();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Agent agent : agents) {
			System.out.println(agent);
			Ticket ticket = new Ticket();
			ticket.setAssignedToUserType("agent");
			ticket.setAssignedToId(agent.getId());
			List<Ticket> tickets = ticketRepository.findAll(Example.of(ticket));
			if (tickets.size() >0) {
				long totalNumberOfClosedTicket = 0;
				long totalNumberOfOpenTicket = 0;
				long totalNumberOfTicket = 0;
				for (Ticket ticket2 : tickets) {
					if (ticket2.getStatus().equalsIgnoreCase("Closed")) {
						totalNumberOfClosedTicket++;
					} else if (ticket2.getStatus().equalsIgnoreCase("Open")) {
						totalNumberOfOpenTicket++;
					}
					totalNumberOfTicket++;
				}
				long responseRate =totalNumberOfClosedTicket * 100/ totalNumberOfTicket ;

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("agentName", agent.getName());
				map.put("ticket", totalNumberOfOpenTicket);
				map.put("responseRate", responseRate);
				list.add(map);
			}
		}
		Comparator<Map<String, Object>> mapComparator = new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> m1, Map<String, Object> m2) {
				// TODO Auto-generated method stub
				Long val1 = (Long) m1.get("responseRate");
				Long val2 = (Long) m2.get("responseRate");
				return val2.compareTo(val1);
			}
		};
		Collections.sort(list, mapComparator);
		Stream<Map<String, Object>> stream = list.stream();
		Stream<Map<String, Object>> lm = stream.limit(4);
		List<Map<String, Object>> list2 = lm.collect(Collectors.toList());
		return list2;
	}

	/**
	 * {@code PUT  /agents} : Updates an existing agent.
	 *
	 * @param agent the agent to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated agent, or with status {@code 400 (Bad Request)} if the
	 *         agent is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the agent couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/updateAgent")
	public ResponseEntity<Agent> updateAgent(@RequestBody Agent agent) throws URISyntaxException {
		log.debug("REST request to update Agent : {}", agent);
		if (agent.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Agent result = agentRepository.save(agent);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agent.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /agents} : get all the agents.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of agents in body.
	 */
	@GetMapping("/listAllAgents")
	public List<Agent> getAllAgents() {
		log.debug("REST request to get all Agents");
		return agentRepository.findAll();
	}

	/**
	 * {@code GET  /agents/:id} : get the "id" agent.
	 *
	 * @param id the id of the agent to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the agent, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/agent/{id}")
	public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
		log.debug("REST request to get Agent : {}", id);
		Optional<Agent> agent = agentRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(agent);
	}

	/**
	 * {@code DELETE  /agents/:id} : delete the "id" agent.
	 *
	 * @param id the id of the agent to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/agent/{id}")
	public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
		log.debug("REST request to delete Agent : {}", id);
		agentRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	@GetMapping("/getTeamMatricsData")
	public List<Map<String,Object>> getTeamMatricsData(){
		List<Agent> agents=agentRepository.findAll();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Agent agent : agents) {
			System.out.println(agent);
			Ticket ticket = new Ticket();
			ticket.setAssignedToUserType("agent");
			ticket.setAssignedToId(agent.getId());
			ticket.setAssociatedEntityName("alert");
			List<Ticket> tickets = ticketRepository.findAll(Example.of(ticket),Sort.by(Direction.DESC, "createdOn"));
			if (tickets.size() >0) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("agentName", agent.getName());
				map.put("totalAlert", tickets.size());
				try {
					map.put("timeSinceLastTicketCreated", ChronoUnit.MINUTES.between(tickets.get(0).getCreatedOn(),Instant.now()));
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				listMap.add(map);
				}
		}
		Comparator<Map<String, Object>> mapComparator = new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> m1, Map<String, Object> m2) {
				// TODO Auto-generated method stub
				Integer val1 = (Integer) m1.get("totalAlert");
				Integer val2 = (Integer) m2.get("totalAlert");
				return val2.compareTo(val1);
			}
		};
		Collections.sort(listMap, mapComparator);
		Stream<Map<String, Object>> stream = listMap.stream();
		Stream<Map<String, Object>> lm = stream.limit(4);
		List<Map<String, Object>> list2 = lm.collect(Collectors.toList());
		return list2;

	}
}
