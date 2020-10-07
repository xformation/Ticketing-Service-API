package com.syn.tkt.domain;

import java.io.Serializable;

public class TicketUIObject implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long id;
	private String requesterName;
	private String subject;
	private String status;
	private String priority;
	private String assignedToName;
	private String createDate;

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "TicketUIObject [id=" + id + ", requesterName=" + requesterName + ", subject=" + subject + ", status="
				+ status + ", priority=" + priority + ", assignedTo=" + assignedToName + ", createDate=" + createDate
				+ "]";
	}

}
