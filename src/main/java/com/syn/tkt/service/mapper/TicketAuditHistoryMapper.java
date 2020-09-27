package com.syn.tkt.service.mapper;


import com.syn.tkt.domain.*;
import com.syn.tkt.service.dto.TicketAuditHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketAuditHistory} and its DTO {@link TicketAuditHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketAuditHistoryMapper extends EntityMapper<TicketAuditHistoryDTO, TicketAuditHistory> {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketAuditHistoryDTO toDto(TicketAuditHistory ticketAuditHistory);

    @Mapping(source = "ticketId", target = "ticket")
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
