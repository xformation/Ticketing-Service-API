package com.synectiks.tkt.web.rest;

import com.synectiks.tkt.service.TicketAuditHistoryService;
import com.synectiks.tkt.web.rest.errors.BadRequestAlertException;
import com.synectiks.tkt.service.dto.TicketAuditHistoryDTO;

import com.synectiks.tkt.domain.TicketAuditHistory;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link TicketAuditHistory}.
 */
@RestController
@RequestMapping("/api")
public class TicketAuditHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TicketAuditHistoryResource.class);

    private static final String ENTITY_NAME = "servicedeskTicketAuditHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketAuditHistoryService ticketAuditHistoryService;

    public TicketAuditHistoryResource(TicketAuditHistoryService ticketAuditHistoryService) {
        this.ticketAuditHistoryService = ticketAuditHistoryService;
    }

    /**
     * {@code POST  /ticket-audit-histories} : Create a new ticketAuditHistory.
     *
     * @param ticketAuditHistoryDTO the ticketAuditHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketAuditHistoryDTO, or with status {@code 400 (Bad Request)} if the ticketAuditHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-audit-histories")
    public ResponseEntity<TicketAuditHistoryDTO> createTicketAuditHistory(@Valid @RequestBody TicketAuditHistoryDTO ticketAuditHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save TicketAuditHistory : {}", ticketAuditHistoryDTO);
        if (ticketAuditHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketAuditHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketAuditHistoryDTO result = ticketAuditHistoryService.save(ticketAuditHistoryDTO);
        return ResponseEntity.created(new URI("/api/ticket-audit-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-audit-histories} : Updates an existing ticketAuditHistory.
     *
     * @param ticketAuditHistoryDTO the ticketAuditHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketAuditHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the ticketAuditHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketAuditHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-audit-histories")
    public ResponseEntity<TicketAuditHistoryDTO> updateTicketAuditHistory(@Valid @RequestBody TicketAuditHistoryDTO ticketAuditHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update TicketAuditHistory : {}", ticketAuditHistoryDTO);
        if (ticketAuditHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketAuditHistoryDTO result = ticketAuditHistoryService.save(ticketAuditHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketAuditHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ticket-audit-histories} : get all the ticketAuditHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketAuditHistories in body.
     */
    @GetMapping("/ticket-audit-histories")
    public List<TicketAuditHistoryDTO> getAllTicketAuditHistories() {
        log.debug("REST request to get all TicketAuditHistories");
        return ticketAuditHistoryService.findAll();
    }

    /**
     * {@code GET  /ticket-audit-histories/:id} : get the "id" ticketAuditHistory.
     *
     * @param id the id of the ticketAuditHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketAuditHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-audit-histories/{id}")
    public ResponseEntity<TicketAuditHistoryDTO> getTicketAuditHistory(@PathVariable Long id) {
        log.debug("REST request to get TicketAuditHistory : {}", id);
        Optional<TicketAuditHistoryDTO> ticketAuditHistoryDTO = ticketAuditHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketAuditHistoryDTO);
    }

    /**
     * {@code DELETE  /ticket-audit-histories/:id} : delete the "id" ticketAuditHistory.
     *
     * @param id the id of the ticketAuditHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-audit-histories/{id}")
    public ResponseEntity<Void> deleteTicketAuditHistory(@PathVariable Long id) {
        log.debug("REST request to delete TicketAuditHistory : {}", id);
        ticketAuditHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
