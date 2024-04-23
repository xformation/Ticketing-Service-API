package com.synectiks.tkt.repository;

import com.synectiks.tkt.domain.TicketAuditHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketAuditHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketAuditHistoryRepository extends JpaRepository<TicketAuditHistory, Long> {
}
