package com.syn.tkt.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class TicketUIObject implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long id;
	private String requesterName;
	private String subject;
	private String status;
	private String priority;
	private String assignedToName;
	private LocalDate createDate;
	private LocalDate expectedDateOfCompletion;
	private String type;
	private String tags;
	private String assignedToCompanyName;
	private String requesterCompanyName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getAssignedToName() {
		return assignedToName;
	}
	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public LocalDate getExpectedDateOfCompletion() {
		return expectedDateOfCompletion;
	}
	public void setExpectedDateOfCompletion(LocalDate expectedDateOfCompletion) {
		this.expectedDateOfCompletion = expectedDateOfCompletion;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getAssignedToCompanyName() {
		return assignedToCompanyName;
	}
	public void setAssignedToCompanyName(String assignedToCompanyName) {
		this.assignedToCompanyName = assignedToCompanyName;
	}
	public String getRequesterCompany() {
		return requesterCompanyName;
	}
	public void setRequesterCompanyName(String requesterCompanyName) {
		this.requesterCompanyName = requesterCompanyName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TicketUIObject [id=" + id + ", requesterName=" + requesterName + ", subject=" + subject + ", status="
				+ status + ", priority=" + priority + ", assignedToName=" + assignedToName + ", createDate="
				+ createDate + ", expectedDateOfCompletion=" + expectedDateOfCompletion + ", type=" + type + ", tags="
				+ tags + ", assignedToCompany=" + assignedToCompanyName + ", requesterCompany=" + requesterCompanyName + "]";
	}

	

}
