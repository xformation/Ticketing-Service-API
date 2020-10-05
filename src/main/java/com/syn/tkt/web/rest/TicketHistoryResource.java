package com.syn.tkt.web.rest;

import com.syn.tkt.service.TicketHistoryService;
import com.syn.tkt.web.rest.errors.BadRequestAlertException;
import com.syn.tkt.service.dto.TicketHistoryDTO;

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
 * REST controller for managing {@link com.syn.tkt.domain.TicketHistory}.
 */
@RestController
@RequestMapping("/api")
public class TicketHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TicketHistoryResource.class);

    private static final String ENTITY_NAME = "servicedeskTicketHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketHistoryService ticketHistoryService;

    public TicketHistoryResource(TicketHistoryService ticketHistoryService) {
        this.ticketHistoryService = ticketHistoryService;
    }

    /**
     * {@code POST  /ticket-histories} : Create a new ticketHistory.
     *
     * @param ticketHistoryDTO the ticketHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketHistoryDTO, or with status {@code 400 (Bad Request)} if the ticketHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-histories")
    public ResponseEntity<TicketHistoryDTO> createTicketHistory(@Valid @RequestBody TicketHistoryDTO ticketHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save TicketHistory : {}", ticketHistoryDTO);
        if (ticketHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketHistoryDTO result = ticketHistoryService.save(ticketHistoryDTO);
        return ResponseEntity.created(new URI("/api/ticket-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-histories} : Updates an existing ticketHistory.
     *
     * @param ticketHistoryDTO the ticketHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the ticketHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-histories")
    public ResponseEntity<TicketHistoryDTO> updateTicketHistory(@Valid @RequestBody TicketHistoryDTO ticketHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update TicketHistory : {}", ticketHistoryDTO);
        if (ticketHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketHistoryDTO result = ticketHistoryService.save(ticketHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ticket-histories} : get all the ticketHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketHistories in body.
     */
    @GetMapping("/ticket-histories")
    public List<TicketHistoryDTO> getAllTicketHistories() {
        log.debug("REST request to get all TicketHistories");
        return ticketHistoryService.findAll();
    }

    /**
     * {@code GET  /ticket-histories/:id} : get the "id" ticketHistory.
     *
     * @param id the id of the ticketHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-histories/{id}")
    public ResponseEntity<TicketHistoryDTO> getTicketHistory(@PathVariable Long id) {
        log.debug("REST request to get TicketHistory : {}", id);
        Optional<TicketHistoryDTO> ticketHistoryDTO = ticketHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketHistoryDTO);
    }

    /**
     * {@code DELETE  /ticket-histories/:id} : delete the "id" ticketHistory.
     *
     * @param id the id of the ticketHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-histories/{id}")
    public ResponseEntity<Void> deleteTicketHistory(@PathVariable Long id) {
        log.debug("REST request to delete TicketHistory : {}", id);
        ticketHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
