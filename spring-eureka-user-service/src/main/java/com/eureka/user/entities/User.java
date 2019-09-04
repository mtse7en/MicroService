package com.eureka.user.entities;

public class User {
	private String userId;
	private String eMail;

	public User(String userId, String eMail) {
		this.userId = userId;
		this.eMail = eMail;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
