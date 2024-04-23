package com.synectiks.tkt.service.dto;

import com.synectiks.tkt.domain.TicketAuditReport;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link TicketAuditReport} entity.
 */
public class TicketAuditReportDTO implements Serializable {

    private Long id;

    private String property;

    private String oldValue;

    private String newValue;

    private String updatedBy;

    private Instant actionTime;

    @Size(max = 5000)
    private String comments;


    private Long ticketId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getActionTime() {
        return actionTime;
    }

    public void setActionTime(Instant actionTime) {
        this.actionTime = actionTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(o instanceof TicketAuditReportDTO)) {
            return false;
        }

        return id != null && id.equals(((TicketAuditReportDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketAuditReportDTO{" +
            "id=" + getId() +
            ", property='" + getProperty() + "'" +
            ", oldValue='" + getOldValue() + "'" +
            ", newValue='" + getNewValue() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", actionTime='" + getActionTime() + "'" +
            ", comments='" + getComments() + "'" +
            ", ticketId=" + getTicketId() +
            "}";
    }
}
