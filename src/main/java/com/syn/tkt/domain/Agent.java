package com.syn.tkt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "alternate_email")
    private String alternateEmail;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "twitter_handle")
    private String twitterHandle;

    @Column(name = "unique_external_id")
    private String uniqueExternalId;

    @Column(name = "image_location")
    private String imageLocation;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JsonIgnoreProperties(value = "agents", allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Agent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Agent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Agent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public Agent primaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public Agent alternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
        return this;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public Agent workPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Agent mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public Agent twitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
        return this;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getUniqueExternalId() {
        return uniqueExternalId;
    }

    public Agent uniqueExternalId(String uniqueExternalId) {
        this.uniqueExternalId = uniqueExternalId;
        return this;
    }

    public void setUniqueExternalId(String uniqueExternalId) {
        this.uniqueExternalId = uniqueExternalId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public Agent imageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
        return this;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public Agent imageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
        return this;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getAddress() {
        return address;
    }

    public Agent address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public Agent company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agent)) {
            return false;
        }
        return id != null && id.equals(((Agent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", title='" + getTitle() + "'" +
            ", primaryEmail='" + getPrimaryEmail() + "'" +
            ", alternateEmail='" + getAlternateEmail() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", twitterHandle='" + getTwitterHandle() + "'" +
            ", uniqueExternalId='" + getUniqueExternalId() + "'" +
            ", imageLocation='" + getImageLocation() + "'" +
            ", imageFileName='" + getImageFileName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
