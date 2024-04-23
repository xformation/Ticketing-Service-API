package com.synectiks.tkt.service.mapper;


import com.synectiks.tkt.domain.Agent;
import com.synectiks.tkt.service.dto.AgentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Agent} and its DTO {@link AgentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface AgentMapper extends EntityMapper<AgentDTO, Agent> {

    @Mapping(source = "company.id", target = "companyId")
    AgentDTO toDto(Agent agent);

    @Mapping(source = "companyId", target = "company.id")
    Agent toEntity(AgentDTO agentDTO);

    default Agent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(id);
        return agent;
    }
}
