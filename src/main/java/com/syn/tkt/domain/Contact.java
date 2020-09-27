package com.syn.tkt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_name")
    private String userName;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "contacts", allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Contact userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public Contact title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public Contact primaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public Contact alternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
        return this;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public Contact workPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Contact mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public Contact twitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
        return this;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getUniqueExternalId() {
        return uniqueExternalId;
    }

    public Contact uniqueExternalId(String uniqueExternalId) {
        this.uniqueExternalId = uniqueExternalId;
        return this;
    }

    public void setUniqueExternalId(String uniqueExternalId) {
        this.uniqueExternalId = uniqueExternalId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public Contact imageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
        return this;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public Contact imageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
        return this;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Company getCompany() {
        return company;
    }

    public Contact company(Company company) {
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
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", title='" + getTitle() + "'" +
            ", primaryEmail='" + getPrimaryEmail() + "'" +
            ", alternateEmail='" + getAlternateEmail() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", twitterHandle='" + getTwitterHandle() + "'" +
            ", uniqueExternalId='" + getUniqueExternalId() + "'" +
            ", imageLocation='" + getImageLocation() + "'" +
            ", imageFileName='" + getImageFileName() + "'" +
            "}";
    }
}
