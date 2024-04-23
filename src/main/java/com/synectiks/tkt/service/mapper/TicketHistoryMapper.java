package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.TicketHistory;
import com.synectiks.tkt.service.dto.TicketHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link TicketHistory} and its DTO {@link TicketHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketHistoryMapper extends EntityMapper<TicketHistoryDTO, TicketHistory> {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketHistoryDTO toDto(TicketHistory ticketHistory);

    @Mapping(source = "ticketId", target = "ticket.id")
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
