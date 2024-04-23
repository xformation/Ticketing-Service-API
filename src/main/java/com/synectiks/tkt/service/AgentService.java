package com.synectiks.tkt.service;

import com.synectiks.tkt.service.dto.AgentDTO;
import com.synectiks.tkt.domain.Agent;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Agent}.
 */
public interface AgentService {

    /**
     * Save a agent.
     *
     * @param agentDTO the entity to save.
     * @return the persisted entity.
     */
    AgentDTO save(AgentDTO agentDTO);

    /**
     * Get all the agents.
     *
     * @return the list of entities.
     */
    List<AgentDTO> findAll();


    /**
     * Get the "id" agent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgentDTO> findOne(Long id);

    /**
     * Delete the "id" agent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
