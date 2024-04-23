package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.Contact;
import com.synectiks.tkt.service.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "company.id", target = "companyId")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "companyId", target = "company.id")
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
