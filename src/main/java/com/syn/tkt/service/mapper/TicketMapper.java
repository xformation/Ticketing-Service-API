package com.syn.tkt.service.mapper;


import com.syn.tkt.domain.*;
import com.syn.tkt.service.dto.TicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, AgentMapper.class})
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "agent.id", target = "agentId")
    TicketDTO toDto(Ticket ticket);

    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "agentId", target = "agent")
    Ticket toEntity(TicketDTO ticketDTO);

    default Ticket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
