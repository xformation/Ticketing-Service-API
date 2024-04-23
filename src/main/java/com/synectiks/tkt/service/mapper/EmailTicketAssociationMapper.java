package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.EmailTicketAssociation;
import com.synectiks.tkt.service.dto.EmailTicketAssociationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EmailTicketAssociation} and its DTO {@link EmailTicketAssociationDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmailMapper.class, TicketMapper.class})
public interface EmailTicketAssociationMapper extends EntityMapper<EmailTicketAssociationDTO, EmailTicketAssociation> {

    @Mapping(source = "email.id", target = "emailId")
    @Mapping(source = "ticket.id", target = "ticketId")
    EmailTicketAssociationDTO toDto(EmailTicketAssociation emailTicketAssociation);

    @Mapping(source = "emailId", target = "email.id")
    @Mapping(source = "ticketId", target = "ticket.id")
    EmailTicketAssociation toEntity(EmailTicketAssociationDTO emailTicketAssociationDTO);

    default EmailTicketAssociation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailTicketAssociation emailTicketAssociation = new EmailTicketAssociation();
        emailTicketAssociation.setId(id);
        return emailTicketAssociation;
    }
}
