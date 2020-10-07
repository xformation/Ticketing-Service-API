package com.syn.tkt.domain;

import java.io.Serializable;

public class ContactCompanyVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String title;
    private String name;
    private String company;
    private String primaryEmail;
    private String workPhone;
    private String uniqueExternalId;
    private String twitterHandle;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPrimaryEmail() {
		return primaryEmail;
	}
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getUniqueExternalId() {
		return uniqueExternalId;
	}
	public void setUniqueExternalId(String uniqueExternalId) {
		this.uniqueExternalId = uniqueExternalId;
	}
	public String getTwitterHandle() {
		return twitterHandle;
	}
	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ContactCompanyVo [title=" + title + ", name=" + name + ", company=" + company + ", primaryEmail="
				+ primaryEmail + ", workPhone=" + workPhone + ", uniqueExternalId=" + uniqueExternalId
				+ ", twitterHandle=" + twitterHandle + "]";
	}
    

}
