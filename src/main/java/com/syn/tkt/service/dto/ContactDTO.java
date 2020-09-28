package com.syn.tkt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.syn.tkt.domain.Contact} entity.
 */
public class ContactDTO implements Serializable {
    
    private Long id;

    private String userName;

    private String title;

    private String primaryEmail;

    private String alternateEmail;

    private String workPhone;

    private String mobilePhone;

    private String twitterHandle;

    private String uniqueExternalId;

    private String imageLocation;

    private String imageFileName;


    private Long companyId;
    public ContactDTO() {
    	
    }
    
    
    public ContactDTO(String userName, String title, String primaryEmail, String alternateEmail, String workPhone,
			String mobilePhone, String twitterHandle, String uniqueExternalId, String imageLocation,
			String imageFileName, Long companyId) {
		super();
		this.userName = userName;
		this.title = title;
		this.primaryEmail = primaryEmail;
		this.alternateEmail = alternateEmail;
		this.workPhone = workPhone;
		this.mobilePhone = mobilePhone;
		this.twitterHandle = twitterHandle;
		this.uniqueExternalId = uniqueExternalId;
		this.imageLocation = imageLocation;
		this.imageFileName = imageFileName;
		this.companyId = companyId;
	}


	public ContactDTO(Long id, String userName, String title, String primaryEmail, String alternateEmail,
			String workPhone, String mobilePhone, String twitterHandle, String uniqueExternalId, String imageLocation,
			String imageFileName, Long companyId) {
		super();
		this.id = id;
		this.userName = userName;
		this.title = title;
		this.primaryEmail = primaryEmail;
		this.alternateEmail = alternateEmail;
		this.workPhone = workPhone;
		this.mobilePhone = mobilePhone;
		this.twitterHandle = twitterHandle;
		this.uniqueExternalId = uniqueExternalId;
		this.imageLocation = imageLocation;
		this.imageFileName = imageFileName;
		this.companyId = companyId;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getUniqueExternalId() {
        return uniqueExternalId;
    }

    public void setUniqueExternalId(String uniqueExternalId) {
        this.uniqueExternalId = uniqueExternalId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
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
        if (!(o instanceof ContactDTO)) {
            return false;
        }

        return id != null && id.equals(((ContactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactDTO{" +
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
            ", companyId=" + getCompanyId() +
            "}";
    }
}
