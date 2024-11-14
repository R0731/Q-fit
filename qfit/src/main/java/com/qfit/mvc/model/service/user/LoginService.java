package com.qfit.mvc.model.service.user;

import java.util.Map;

import com.qfit.mvc.model.dto.user.User;

public interface LoginService {
		
	boolean correctPassword(int id, String userPassword);
	
	public String login(String userId, String userPassword);
	
}
