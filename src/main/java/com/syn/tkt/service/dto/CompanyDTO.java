package com.syn.tkt.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.syn.tkt.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {
    
    private Long id;

    private String companyName;

    @Size(max = 5000)
    private String description;

    @Size(max = 5000)
    private String notes;

    private String healthScore;

    private String accountTier;

    private LocalDate renewalDate;

    private String industry;

    private String companyLogoFileName;

    private String companyLogoFileLocation;

    private String domain;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(String healthScore) {
        this.healthScore = healthScore;
    }

    public String getAccountTier() {
        return accountTier;
    }

    public void setAccountTier(String accountTier) {
        this.accountTier = accountTier;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyLogoFileName() {
        return companyLogoFileName;
    }

    public void setCompanyLogoFileName(String companyLogoFileName) {
        this.companyLogoFileName = companyLogoFileName;
    }

    public String getCompanyLogoFileLocation() {
        return companyLogoFileLocation;
    }

    public void setCompanyLogoFileLocation(String companyLogoFileLocation) {
        this.companyLogoFileLocation = companyLogoFileLocation;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
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
            "}";
    }
}
