package com.synectiks.tkt.service;

import com.synectiks.tkt.service.dto.TicketAuditHistoryDTO;
import com.synectiks.tkt.domain.TicketAuditHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TicketAuditHistory}.
 */
public interface TicketAuditHistoryService {

    /**
     * Save a ticketAuditHistory.
     *
     * @param ticketAuditHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    TicketAuditHistoryDTO save(TicketAuditHistoryDTO ticketAuditHistoryDTO);

    /**
     * Get all the ticketAuditHistories.
     *
     * @return the list of entities.
     */
    List<TicketAuditHistoryDTO> findAll();


    /**
     * Get the "id" ticketAuditHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketAuditHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" ticketAuditHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
