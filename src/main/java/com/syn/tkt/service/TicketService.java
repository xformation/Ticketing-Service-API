package com.syn.tkt.service;

import com.syn.tkt.service.dto.TicketDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.syn.tkt.domain.Ticket}.
 */
public interface TicketService {

    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save.
     * @return the persisted entity.
     */
    TicketDTO save(TicketDTO ticketDTO);

    /**
     * Get all the tickets.
     *
     * @return the list of entities.
     */
    List<TicketDTO> findAll();


    /**
     * Get the "id" ticket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketDTO> findOne(Long id);

    /**
     * Delete the "id" ticket.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
