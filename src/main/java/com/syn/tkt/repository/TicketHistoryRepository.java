package com.syn.tkt.repository;

import com.syn.tkt.domain.TicketHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {
}
