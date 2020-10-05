package com.syn.tkt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.syn.tkt.domain.Agent} entity.
 */
public class AgentDTO implements Serializable {
    
    private Long id;

    private String name;

    private String description;


    private Long companyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgentDTO)) {
            return false;
        }

        return id != null && id.equals(((AgentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
