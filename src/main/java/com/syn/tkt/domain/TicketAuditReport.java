package com.syn.tkt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TicketAuditReport.
 */
@Entity
@Table(name = "ticket_audit_report")
public class TicketAuditReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "property")
    private String property;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "action_time")
    private Instant actionTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ticketAuditReports", allowSetters = true)
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public TicketAuditReport property(String property) {
        this.property = property;
        return this;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getOldValue() {
        return oldValue;
    }

    public TicketAuditReport oldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public TicketAuditReport newValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getUser() {
        return user;
    }

    public TicketAuditReport user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Instant getActionTime() {
        return actionTime;
    }

    public TicketAuditReport actionTime(Instant actionTime) {
        this.actionTime = actionTime;
        return this;
    }

    public void setActionTime(Instant actionTime) {
        this.actionTime = actionTime;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public TicketAuditReport ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketAuditReport)) {
            return false;
        }
        return id != null && id.equals(((TicketAuditReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketAuditReport{" +
            "id=" + getId() +
            ", property='" + getProperty() + "'" +
            ", oldValue='" + getOldValue() + "'" +
            ", newValue='" + getNewValue() + "'" +
            ", user='" + getUser() + "'" +
            ", actionTime='" + getActionTime() + "'" +
            "}";
    }
}
