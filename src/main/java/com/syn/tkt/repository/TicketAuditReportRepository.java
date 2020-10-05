package com.syn.tkt.repository;

import com.syn.tkt.domain.TicketAuditReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketAuditReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketAuditReportRepository extends JpaRepository<TicketAuditReport, Long> {
}
