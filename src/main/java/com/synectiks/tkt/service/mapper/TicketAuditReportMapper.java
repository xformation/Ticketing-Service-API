package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.TicketAuditReport;
import com.synectiks.tkt.service.dto.TicketAuditReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link TicketAuditReport} and its DTO {@link TicketAuditReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketAuditReportMapper extends EntityMapper<TicketAuditReportDTO, TicketAuditReport> {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketAuditReportDTO toDto(TicketAuditReport ticketAuditReport);

    @Mapping(source = "ticketId", target = "ticket.id")
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
