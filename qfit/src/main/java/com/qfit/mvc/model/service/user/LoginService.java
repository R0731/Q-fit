package com.qfit.mvc.model.service.user;

import java.util.Map;

import com.qfit.mvc.model.dto.user.User;

public interface LoginService {
	
	// 비밀번호 확인
	boolean correctPassword(String userId, String userPassword);
	
	// 로그인
	public String login(String userId, String userPassword);
	
}
