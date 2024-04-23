package com.synectiks.tkt.service;

import com.synectiks.tkt.domain.TicketHistory;
import com.synectiks.tkt.service.dto.TicketHistoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TicketHistory}.
 */
public interface TicketHistoryService {

    /**
     * Save a ticketHistory.
     *
     * @param ticketHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    TicketHistoryDTO save(TicketHistoryDTO ticketHistoryDTO);

    /**
     * Get all the ticketHistories.
     *
     * @return the list of entities.
     */
    List<TicketHistoryDTO> findAll();


    /**
     * Get the "id" ticketHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" ticketHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
