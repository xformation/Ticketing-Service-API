package com.synectiks.tkt.repository;

import com.synectiks.tkt.domain.TicketAuditReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketAuditReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketAuditReportRepository extends JpaRepository<TicketAuditReport, Long> {
}
