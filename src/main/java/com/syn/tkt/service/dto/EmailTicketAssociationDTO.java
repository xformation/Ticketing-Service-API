package com.syn.tkt.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.syn.tkt.domain.EmailTicketAssociation} entity.
 */
public class EmailTicketAssociationDTO implements Serializable {
    
    private Long id;

    private String updatedBy;

    private Instant updatedOn;


    private Long emailId;

    private Long ticketId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTicketAssociationDTO)) {
            return false;
        }

        return id != null && id.equals(((EmailTicketAssociationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailTicketAssociationDTO{" +
            "id=" + getId() +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", emailId=" + getEmailId() +
            ", ticketId=" + getTicketId() +
            "}";
    }
}
