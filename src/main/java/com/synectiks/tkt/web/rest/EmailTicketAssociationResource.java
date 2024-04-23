package com.synectiks.tkt.web.rest;

import com.synectiks.tkt.service.EmailTicketAssociationService;
import com.synectiks.tkt.web.rest.errors.BadRequestAlertException;
import com.synectiks.tkt.service.dto.EmailTicketAssociationDTO;

import com.synectiks.tkt.domain.EmailTicketAssociation;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link EmailTicketAssociation}.
 */
@RestController
@RequestMapping("/api")
public class EmailTicketAssociationResource {

    private final Logger log = LoggerFactory.getLogger(EmailTicketAssociationResource.class);

    private static final String ENTITY_NAME = "servicedeskEmailTicketAssociation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailTicketAssociationService emailTicketAssociationService;

    public EmailTicketAssociationResource(EmailTicketAssociationService emailTicketAssociationService) {
        this.emailTicketAssociationService = emailTicketAssociationService;
    }

    /**
     * {@code POST  /email-ticket-associations} : Create a new emailTicketAssociation.
     *
     * @param emailTicketAssociationDTO the emailTicketAssociationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailTicketAssociationDTO, or with status {@code 400 (Bad Request)} if the emailTicketAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-ticket-associations")
    public ResponseEntity<EmailTicketAssociationDTO> createEmailTicketAssociation(@RequestBody EmailTicketAssociationDTO emailTicketAssociationDTO) throws URISyntaxException {
        log.debug("REST request to save EmailTicketAssociation : {}", emailTicketAssociationDTO);
        if (emailTicketAssociationDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailTicketAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailTicketAssociationDTO result = emailTicketAssociationService.save(emailTicketAssociationDTO);
        return ResponseEntity.created(new URI("/api/email-ticket-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-ticket-associations} : Updates an existing emailTicketAssociation.
     *
     * @param emailTicketAssociationDTO the emailTicketAssociationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailTicketAssociationDTO,
     * or with status {@code 400 (Bad Request)} if the emailTicketAssociationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailTicketAssociationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-ticket-associations")
    public ResponseEntity<EmailTicketAssociationDTO> updateEmailTicketAssociation(@RequestBody EmailTicketAssociationDTO emailTicketAssociationDTO) throws URISyntaxException {
        log.debug("REST request to update EmailTicketAssociation : {}", emailTicketAssociationDTO);
        if (emailTicketAssociationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailTicketAssociationDTO result = emailTicketAssociationService.save(emailTicketAssociationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailTicketAssociationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-ticket-associations} : get all the emailTicketAssociations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailTicketAssociations in body.
     */
    @GetMapping("/email-ticket-associations")
    public List<EmailTicketAssociationDTO> getAllEmailTicketAssociations() {
        log.debug("REST request to get all EmailTicketAssociations");
        return emailTicketAssociationService.findAll();
    }

    /**
     * {@code GET  /email-ticket-associations/:id} : get the "id" emailTicketAssociation.
     *
     * @param id the id of the emailTicketAssociationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailTicketAssociationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-ticket-associations/{id}")
    public ResponseEntity<EmailTicketAssociationDTO> getEmailTicketAssociation(@PathVariable Long id) {
        log.debug("REST request to get EmailTicketAssociation : {}", id);
        Optional<EmailTicketAssociationDTO> emailTicketAssociationDTO = emailTicketAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailTicketAssociationDTO);
    }

    /**
     * {@code DELETE  /email-ticket-associations/:id} : delete the "id" emailTicketAssociation.
     *
     * @param id the id of the emailTicketAssociationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-ticket-associations/{id}")
    public ResponseEntity<Void> deleteEmailTicketAssociation(@PathVariable Long id) {
        log.debug("REST request to delete EmailTicketAssociation : {}", id);
        emailTicketAssociationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
