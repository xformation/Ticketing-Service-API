package com.synectiks.tkt.service;

import com.synectiks.tkt.service.dto.EmailTicketAssociationDTO;
import com.synectiks.tkt.domain.EmailTicketAssociation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EmailTicketAssociation}.
 */
public interface EmailTicketAssociationService {

    /**
     * Save a emailTicketAssociation.
     *
     * @param emailTicketAssociationDTO the entity to save.
     * @return the persisted entity.
     */
    EmailTicketAssociationDTO save(EmailTicketAssociationDTO emailTicketAssociationDTO);

    /**
     * Get all the emailTicketAssociations.
     *
     * @return the list of entities.
     */
    List<EmailTicketAssociationDTO> findAll();


    /**
     * Get the "id" emailTicketAssociation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailTicketAssociationDTO> findOne(Long id);

    /**
     * Delete the "id" emailTicketAssociation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
