package com.synectiks.tkt.service;

import com.synectiks.tkt.service.dto.TicketAuditReportDTO;
import com.synectiks.tkt.domain.TicketAuditReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TicketAuditReport}.
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
