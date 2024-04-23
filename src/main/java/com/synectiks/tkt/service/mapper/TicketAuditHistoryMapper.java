package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.TicketAuditHistory;
import com.synectiks.tkt.service.dto.TicketAuditHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link TicketAuditHistory} and its DTO {@link TicketAuditHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketAuditHistoryMapper extends EntityMapper<TicketAuditHistoryDTO, TicketAuditHistory> {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketAuditHistoryDTO toDto(TicketAuditHistory ticketAuditHistory);

    @Mapping(source = "ticketId", target = "ticket.id")
    TicketAuditHistory toEntity(TicketAuditHistoryDTO ticketAuditHistoryDTO);

    default TicketAuditHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TicketAuditHistory ticketAuditHistory = new TicketAuditHistory();
        ticketAuditHistory.setId(id);
        return ticketAuditHistory;
    }
}
