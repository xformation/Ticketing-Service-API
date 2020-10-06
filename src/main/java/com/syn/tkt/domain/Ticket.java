package com.syn.tkt.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Size(max = 6000)
    @Column(name = "description", length = 6000)
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Size(max = 5000)
    @Column(name = "comments", length = 5000)
    private String comments;

    @Column(name = "expected_date_of_completion")
    private LocalDate expectedDateOfCompletion;

    @Column(name = "actual_date_of_completion")
    private LocalDate actualDateOfCompletion;

    @Column(name = "tag")
    private String tag;

    @Column(name = "assigned_to_user_type")
    private String assignedToUserType;

    @Column(name = "requester_user_type")
    private String requesterUserType;

    @Column(name = "assigned_to_id")
    private Long assignedToId;

    @Column(name = "requester_id")
    private Long requesterId;

    @Column(name = "associated_entity_name")
    private String associatedEntityName;

    @Column(name = "associated_entity_id")
    private String associatedEntityId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Ticket subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public Ticket description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public Ticket type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Ticket status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public Ticket priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Ticket createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Ticket createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Ticket updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Ticket updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getComments() {
        return comments;
    }

    public Ticket comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getExpectedDateOfCompletion() {
        return expectedDateOfCompletion;
    }

    public Ticket expectedDateOfCompletion(LocalDate expectedDateOfCompletion) {
        this.expectedDateOfCompletion = expectedDateOfCompletion;
        return this;
    }

    public void setExpectedDateOfCompletion(LocalDate expectedDateOfCompletion) {
        this.expectedDateOfCompletion = expectedDateOfCompletion;
    }

    public LocalDate getActualDateOfCompletion() {
        return actualDateOfCompletion;
    }

    public Ticket actualDateOfCompletion(LocalDate actualDateOfCompletion) {
        this.actualDateOfCompletion = actualDateOfCompletion;
        return this;
    }

    public void setActualDateOfCompletion(LocalDate actualDateOfCompletion) {
        this.actualDateOfCompletion = actualDateOfCompletion;
    }

    public String getTag() {
        return tag;
    }

    public Ticket tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAssignedToUserType() {
        return assignedToUserType;
    }

    public Ticket assignedToUserType(String assignedToUserType) {
        this.assignedToUserType = assignedToUserType;
        return this;
    }

    public void setAssignedToUserType(String assignedToUserType) {
        this.assignedToUserType = assignedToUserType;
    }

    public String getRequesterUserType() {
        return requesterUserType;
    }

    public Ticket requesterUserType(String requesterUserType) {
        this.requesterUserType = requesterUserType;
        return this;
    }

    public void setRequesterUserType(String requesterUserType) {
        this.requesterUserType = requesterUserType;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public Ticket assignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
        return this;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public Ticket requesterId(Long requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public String getAssociatedEntityName() {
        return associatedEntityName;
    }

    public Ticket associatedEntityName(String associatedEntityName) {
        this.associatedEntityName = associatedEntityName;
        return this;
    }

    public void setAssociatedEntityName(String associatedEntityName) {
        this.associatedEntityName = associatedEntityName;
    }

    public String getAssociatedEntityId() {
        return associatedEntityId;
    }

    public Ticket associatedEntityId(String associatedEntityId) {
        this.associatedEntityId = associatedEntityId;
        return this;
    }

    public void setAssociatedEntityId(String associatedEntityId) {
        this.associatedEntityId = associatedEntityId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
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
            ", assignedToUserType='" + getAssignedToUserType() + "'" +
            ", requesterUserType='" + getRequesterUserType() + "'" +
            ", assignedToId=" + getAssignedToId() +
            ", requesterId=" + getRequesterId() +
            ", associatedEntityName='" + getAssociatedEntityName() + "'" +
            ", associatedEntityId='" + getAssociatedEntityId() + "'" +
            "}";
    }
}
