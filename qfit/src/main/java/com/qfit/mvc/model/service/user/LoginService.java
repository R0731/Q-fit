package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.User;

public interface LoginService {
		
	boolean correctPassword(int id, String userPassword);
	
	public User login(String userId, String userPassword);
	
}
