package com.synectiks.tkt.web.rest;

import com.synectiks.tkt.service.TicketAuditReportService;
import com.synectiks.tkt.web.rest.errors.BadRequestAlertException;
import com.synectiks.tkt.service.dto.TicketAuditReportDTO;

import com.synectiks.tkt.domain.TicketAuditReport;
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
 * REST controller for managing {@link TicketAuditReport}.
 */
@RestController
@RequestMapping("/api")
public class TicketAuditReportResource {

    private final Logger log = LoggerFactory.getLogger(TicketAuditReportResource.class);

    private static final String ENTITY_NAME = "servicedeskTicketAuditReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketAuditReportService ticketAuditReportService;

    public TicketAuditReportResource(TicketAuditReportService ticketAuditReportService) {
        this.ticketAuditReportService = ticketAuditReportService;
    }

    /**
     * {@code POST  /ticket-audit-reports} : Create a new ticketAuditReport.
     *
     * @param ticketAuditReportDTO the ticketAuditReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketAuditReportDTO, or with status {@code 400 (Bad Request)} if the ticketAuditReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-audit-reports")
    public ResponseEntity<TicketAuditReportDTO> createTicketAuditReport(@Valid @RequestBody TicketAuditReportDTO ticketAuditReportDTO) throws URISyntaxException {
        log.debug("REST request to save TicketAuditReport : {}", ticketAuditReportDTO);
        if (ticketAuditReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketAuditReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketAuditReportDTO result = ticketAuditReportService.save(ticketAuditReportDTO);
        return ResponseEntity.created(new URI("/api/ticket-audit-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-audit-reports} : Updates an existing ticketAuditReport.
     *
     * @param ticketAuditReportDTO the ticketAuditReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketAuditReportDTO,
     * or with status {@code 400 (Bad Request)} if the ticketAuditReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketAuditReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-audit-reports")
    public ResponseEntity<TicketAuditReportDTO> updateTicketAuditReport(@Valid @RequestBody TicketAuditReportDTO ticketAuditReportDTO) throws URISyntaxException {
        log.debug("REST request to update TicketAuditReport : {}", ticketAuditReportDTO);
        if (ticketAuditReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketAuditReportDTO result = ticketAuditReportService.save(ticketAuditReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketAuditReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ticket-audit-reports} : get all the ticketAuditReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketAuditReports in body.
     */
    @GetMapping("/ticket-audit-reports")
    public List<TicketAuditReportDTO> getAllTicketAuditReports() {
        log.debug("REST request to get all TicketAuditReports");
        return ticketAuditReportService.findAll();
    }

    /**
     * {@code GET  /ticket-audit-reports/:id} : get the "id" ticketAuditReport.
     *
     * @param id the id of the ticketAuditReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketAuditReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-audit-reports/{id}")
    public ResponseEntity<TicketAuditReportDTO> getTicketAuditReport(@PathVariable Long id) {
        log.debug("REST request to get TicketAuditReport : {}", id);
        Optional<TicketAuditReportDTO> ticketAuditReportDTO = ticketAuditReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketAuditReportDTO);
    }

    /**
     * {@code DELETE  /ticket-audit-reports/:id} : delete the "id" ticketAuditReport.
     *
     * @param id the id of the ticketAuditReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-audit-reports/{id}")
    public ResponseEntity<Void> deleteTicketAuditReport(@PathVariable Long id) {
        log.debug("REST request to delete TicketAuditReport : {}", id);
        ticketAuditReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
