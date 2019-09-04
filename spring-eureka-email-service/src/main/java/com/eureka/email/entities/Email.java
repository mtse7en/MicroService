package com.eureka.email.entities;

public class Email {
 
	private int id;
	
	private String userId;
	private String mailHeader;
	private String mailContent;
	private String sourceAddress;
	private String destinationAddress;

	public Email( String userId,String mailHeader, String mailContent,String sourceAddress,String destinationAddress) {
		 this.setUserId(userId);
		 this.mailHeader= mailHeader;
		 this.mailContent= mailContent;
		 this.sourceAddress = sourceAddress;
		 this.destinationAddress = destinationAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

 

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getMailHeader() {
		return mailHeader;
	}

	public void setMailHeader(String mailHeader) {
		this.mailHeader = mailHeader;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	 
	 
	
}

