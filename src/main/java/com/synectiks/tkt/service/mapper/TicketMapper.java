package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.Ticket;
import com.synectiks.tkt.service.dto.TicketDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {



    default Ticket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
