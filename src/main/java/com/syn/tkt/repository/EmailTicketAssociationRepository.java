package com.syn.tkt.repository;

import com.syn.tkt.domain.EmailTicketAssociation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmailTicketAssociation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTicketAssociationRepository extends JpaRepository<EmailTicketAssociation, Long> {
}
