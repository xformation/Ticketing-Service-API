package com.syn.tkt.service;

import com.syn.tkt.service.dto.TicketAuditReportDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.syn.tkt.domain.TicketAuditReport}.
 */
public interface TicketAuditReportService {

    /**
     * Save a ticketAuditReport.
     *
     * @param ticketAuditReportDTO the entity to save.
     * @return the persisted entity.
     */
    TicketAuditReportDTO save(TicketAuditReportDTO ticketAuditReportDTO);

    /**
     * Get all the ticketAuditReports.
     *
     * @return the list of entities.
     */
    List<TicketAuditReportDTO> findAll();


    /**
     * Get the "id" ticketAuditReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketAuditReportDTO> findOne(Long id);

    /**
     * Delete the "id" ticketAuditReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
