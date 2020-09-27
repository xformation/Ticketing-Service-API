package com.syn.tkt.service.mapper;


import com.syn.tkt.domain.*;
import com.syn.tkt.service.dto.EmailDTO;

import org.mapstruct.*;

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
