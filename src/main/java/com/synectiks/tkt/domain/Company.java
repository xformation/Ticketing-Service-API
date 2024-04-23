package com.synectiks.tkt.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Size(max = 5000)
    @Column(name = "notes", length = 5000)
    private String notes;

    @Column(name = "health_score")
    private String healthScore;

    @Column(name = "account_tier")
    private String accountTier;

    @Column(name = "renewal_date")
    private LocalDate renewalDate;

    @Column(name = "industry")
    private String industry;

    @Column(name = "company_logo_file_name")
    private String companyLogoFileName;

    @Column(name = "company_logo_file_location")
    private String companyLogoFileLocation;

    @Column(name = "domain")
    private String domain;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public Company notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHealthScore() {
        return healthScore;
    }

    public Company healthScore(String healthScore) {
        this.healthScore = healthScore;
        return this;
    }

    public void setHealthScore(String healthScore) {
        this.healthScore = healthScore;
    }

    public String getAccountTier() {
        return accountTier;
    }

    public Company accountTier(String accountTier) {
        this.accountTier = accountTier;
        return this;
    }

    public void setAccountTier(String accountTier) {
        this.accountTier = accountTier;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public Company renewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
        return this;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getIndustry() {
        return industry;
    }

    public Company industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyLogoFileName() {
        return companyLogoFileName;
    }

    public Company companyLogoFileName(String companyLogoFileName) {
        this.companyLogoFileName = companyLogoFileName;
        return this;
    }

    public void setCompanyLogoFileName(String companyLogoFileName) {
        this.companyLogoFileName = companyLogoFileName;
    }

    public String getCompanyLogoFileLocation() {
        return companyLogoFileLocation;
    }

    public Company companyLogoFileLocation(String companyLogoFileLocation) {
        this.companyLogoFileLocation = companyLogoFileLocation;
        return this;
    }

    public void setCompanyLogoFileLocation(String companyLogoFileLocation) {
        this.companyLogoFileLocation = companyLogoFileLocation;
    }

    public String getDomain() {
        return domain;
    }

    public Company domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Company createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Company createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Company updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Company updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", notes='" + getNotes() + "'" +
            ", healthScore='" + getHealthScore() + "'" +
            ", accountTier='" + getAccountTier() + "'" +
            ", renewalDate='" + getRenewalDate() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", companyLogoFileName='" + getCompanyLogoFileName() + "'" +
            ", companyLogoFileLocation='" + getCompanyLogoFileLocation() + "'" +
            ", domain='" + getDomain() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
