package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.User;

public interface UserService {

	public String signUp(User user);
	
	boolean resign(int id);
	
	public void updatePassword(String password);
	
	public void updateUser(User user);
	
	public boolean isUserIdAvailable(String id);

	public User getUserbyId(String userId);
	
}
