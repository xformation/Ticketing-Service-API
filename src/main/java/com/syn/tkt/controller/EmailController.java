package com.syn.tkt.controller;

import com.syn.tkt.domain.Email;
import com.syn.tkt.domain.EmailTicketAssociation;
import com.syn.tkt.domain.Ticket;
import com.syn.tkt.domain.TicketHistory;
import com.syn.tkt.repository.EmailRepository;
import com.syn.tkt.repository.EmailTicketAssociationRepository;
import com.syn.tkt.repository.TicketHistoryRepository;
import com.syn.tkt.repository.TicketRepository;
import com.syn.tkt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.syn.tkt.domain.Email}.
 */
@RestController
@RequestMapping("/api")
public class EmailController {

	private final Logger log = LoggerFactory.getLogger(EmailController.class);

	private static final String ENTITY_NAME = "servicedeskEmail";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private EmailTicketAssociationRepository emailTicketAssociationRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	/**
	 * {@code POST  /email} : Create a new email.
	 *
	 * @param email the emailDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new emailDTO, or with status {@code 400 (Bad Request)} if
	 *         the email has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/sendEmail")
	public ResponseEntity<Email> sendEmail(@RequestParam String from, @RequestParam String to,
			@RequestParam String subject, @RequestParam String description, @RequestParam String priority,
			@RequestParam String status, @RequestParam String tags, @RequestParam Long ticketId)
			throws URISyntaxException {
		Email email = new Email();
		email.setMailFrom(from);
		email.setMailTo(to);
		email.setStatus(status);
		email.setSubject(subject);
		email.setPriority(priority);
		email.setTags(tags);
		email.description(description);
		log.debug("REST request to save Email : {}", email);
		if (email.getId() != null) {
			throw new BadRequestAlertException("A new email cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Email result = emailRepository.save(email);
		Ticket ticket=ticketRepository.findById(ticketId).get();
		EmailTicketAssociation emailTicketAssociation=new EmailTicketAssociation();
		emailTicketAssociation.setEmail(result);
		emailTicketAssociation.setTicket(ticket);
		emailTicketAssociationRepository.save(emailTicketAssociation);
		return ResponseEntity
				.created(new URI("/api/email/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /email} : Updates an existing email.
	 *
	 * @param emailDTO the emailDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated emailDTO, or with status {@code 400 (Bad Request)} if the
	 *         emailDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the emailDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/email")
	public ResponseEntity<Email> updateEmail(@Valid @RequestBody Email emailDTO) throws URISyntaxException {
		log.debug("REST request to update Email : {}", emailDTO);
		if (emailDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Email result = emailRepository.save(emailDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /email} : get all the email.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of email in body.
	 */
	@GetMapping("/email")
	public List<Email> getAllEmails() {
		log.debug("REST request to get all Emails");
		return emailRepository.findAll();
	}

	/**
	 * {@code GET  /email/:id} : get the "id" email.
	 *
	 * @param id the id of the emailDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the emailDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/email/{id}")
	public ResponseEntity<Email> getEmail(@PathVariable Long id) {
		log.debug("REST request to get Email : {}", id);
		Optional<Email> emailDTO = emailRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(emailDTO);
	}

	/**
	 * {@code DELETE  /email/:id} : delete the "id" email.
	 *
	 * @param id the id of the emailDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/email/{id}")
	public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
		log.debug("REST request to delete Email : {}", id);
		emailRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
