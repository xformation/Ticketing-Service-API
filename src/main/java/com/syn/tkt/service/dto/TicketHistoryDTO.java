package com.syn.tkt.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.syn.tkt.domain.TicketHistory} entity.
 */
public class TicketHistoryDTO implements Serializable {
    
    private Long id;

    private String subject;

    @Size(max = 5000)
    private String description;

    private String type;

    private String status;

    private String priority;

    private Instant createdOn;

    private String createdBy;

    private String updatedBy;

    private Instant updatedOn;

    @Size(max = 5000)
    private String comments;

    private LocalDate expectedDateOfCompletion;

    private LocalDate actualDateOfCompletion;

    private String tag;

    private String assignedUserType;

    private String associatedEntityName;

    private String associatedEntityId;


    private Long ticketId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getExpectedDateOfCompletion() {
        return expectedDateOfCompletion;
    }

    public void setExpectedDateOfCompletion(LocalDate expectedDateOfCompletion) {
        this.expectedDateOfCompletion = expectedDateOfCompletion;
    }

    public LocalDate getActualDateOfCompletion() {
        return actualDateOfCompletion;
    }

    public void setActualDateOfCompletion(LocalDate actualDateOfCompletion) {
        this.actualDateOfCompletion = actualDateOfCompletion;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAssignedUserType() {
        return assignedUserType;
    }

    public void setAssignedUserType(String assignedUserType) {
        this.assignedUserType = assignedUserType;
    }

    public String getAssociatedEntityName() {
        return associatedEntityName;
    }

    public void setAssociatedEntityName(String associatedEntityName) {
        this.associatedEntityName = associatedEntityName;
    }

    public String getAssociatedEntityId() {
        return associatedEntityId;
    }

    public void setAssociatedEntityId(String associatedEntityId) {
        this.associatedEntityId = associatedEntityId;
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
        if (!(o instanceof TicketHistoryDTO)) {
            return false;
        }

        return id != null && id.equals(((TicketHistoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketHistoryDTO{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", comments='" + getComments() + "'" +
            ", expectedDateOfCompletion='" + getExpectedDateOfCompletion() + "'" +
            ", actualDateOfCompletion='" + getActualDateOfCompletion() + "'" +
            ", tag='" + getTag() + "'" +
            ", assignedUserType='" + getAssignedUserType() + "'" +
            ", associatedEntityName='" + getAssociatedEntityName() + "'" +
            ", associatedEntityId='" + getAssociatedEntityId() + "'" +
            ", ticketId=" + getTicketId() +
            "}";
    }
}