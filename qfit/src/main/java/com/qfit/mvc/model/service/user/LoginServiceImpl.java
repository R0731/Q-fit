package com.qfit.mvc.model.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.jwt.JwtUtil;
import com.qfit.mvc.model.dao.user.LoginDao;
import com.qfit.mvc.model.dto.user.User;

@Service
public class LoginServiceImpl implements LoginService{

	private final LoginDao loginDao;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public LoginServiceImpl(LoginDao loginDao, UserService userService, JwtUtil jwtUtil) {
		this.loginDao = loginDao;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	// 패스워드 일치 여부 확인
	@Override
	public boolean correctPassword(String id, String userPassword) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", id);
		info.put("userPassword", userPassword);
		Integer passwordCorrect = loginDao.loginCheck(info);
	    System.out.println("@@@정보확인" + info);

	    System.out.println("@@@@결과확인" + passwordCorrect);
		
	    if (passwordCorrect == null) {
	        System.out.println("null발생");
	        return false;
	    }
	    
		return passwordCorrect == 1;
	}

	// userId, password 로그인
	@Override
	@Transactional
	public String login(String userId, String password) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("userPassword", password);
		Integer loginSuccess = loginDao.loginCheck(info);
		
		if(loginSuccess != null) {
			User loginUser = userService.getUserbyId(userId);
			
			// JWT 토큰 생성
			String token = jwtUtil.createToken(loginUser.getId(), loginUser.getUserId(), loginUser.getUserName(), loginUser.getUserType());
			return token;
		}
		
		throw new IllegalArgumentException("Wrong User");
	}
	
}
