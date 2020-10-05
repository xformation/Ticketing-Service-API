package com.syn.tkt.service.mapper;


import com.syn.tkt.domain.*;
import com.syn.tkt.service.dto.TicketAuditReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketAuditReport} and its DTO {@link TicketAuditReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketAuditReportMapper extends EntityMapper<TicketAuditReportDTO, TicketAuditReport> {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketAuditReportDTO toDto(TicketAuditReport ticketAuditReport);

    @Mapping(source = "ticketId", target = "ticket")
    TicketAuditReport toEntity(TicketAuditReportDTO ticketAuditReportDTO);

    default TicketAuditReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        TicketAuditReport ticketAuditReport = new TicketAuditReport();
        ticketAuditReport.setId(id);
        return ticketAuditReport;
    }
}
