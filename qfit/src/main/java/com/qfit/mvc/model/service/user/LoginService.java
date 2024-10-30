package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.User;

public interface LoginService {
	
	//user가 로그인한 아이디 가져와서 pw 재확인할 때 사용
	public boolean coreectPassword(String userPassword);
	
	public User login(String userId, String userPassword);
	
}
