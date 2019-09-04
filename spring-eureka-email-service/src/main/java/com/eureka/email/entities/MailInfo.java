package com.eureka.email.entities;

public class MailInfo {
	
 
	private String id;
	private String userId;
	private String mailAddress;
	public MailInfo(String userId, String mailAddress) {
		
		this.userId = userId;
		this.mailAddress = mailAddress;
	}
	
	public MailInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
}
