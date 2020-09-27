package com.syn.tkt.service.mapper;


import com.syn.tkt.domain.*;
import com.syn.tkt.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "company.id", target = "companyId")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "companyId", target = "company")
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
