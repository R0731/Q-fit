package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.User;

public interface UserService {
	
	public boolean updateUser(User user);
	
	public boolean isUserIdAvailable(String id);

	public User getUserbyId(String userId);
	
}
