package com.syn.tkt.domain;


import java.io.Serializable;

public class CompanyContactVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String company;
    private int contacts;
    private boolean checkStatus;
    
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getContacts() {
		return contacts;
	}
	public void setContacts(int contacts) {
		this.contacts = contacts;
	}
	public boolean isCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}
}
