package com.qfit.mvc.model.dto.user;

public class LoginRequest {
	
	private String userId;
	private String userPassword;
	
	public LoginRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
