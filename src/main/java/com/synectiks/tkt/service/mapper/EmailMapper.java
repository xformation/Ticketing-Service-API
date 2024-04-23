package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.Email;
import com.synectiks.tkt.service.dto.EmailDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Email} and its DTO {@link EmailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {



    default Email fromId(Long id) {
        if (id == null) {
            return null;
        }
        Email email = new Email();
        email.setId(id);
        return email;
    }
}
