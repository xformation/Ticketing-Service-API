package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.Company;
import com.synectiks.tkt.service.dto.CompanyDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {



    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
