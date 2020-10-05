package com.syn.tkt.service.mapper;


import com.syn.tkt.domain.*;
import com.syn.tkt.service.dto.TicketHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketHistory} and its DTO {@link TicketHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketHistoryMapper extends EntityMapper<TicketHistoryDTO, TicketHistory> {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketHistoryDTO toDto(TicketHistory ticketHistory);

    @Mapping(source = "ticketId", target = "ticket")
    TicketHistory toEntity(TicketHistoryDTO ticketHistoryDTO);

    default TicketHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setId(id);
        return ticketHistory;
    }
}
