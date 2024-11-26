package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.User;

public interface UserService {
	
	// 유저 정보 업데이트
	public boolean updateUser(User user);
	
	// userId 중복 확인
	public boolean isUserIdAvailable(String id);

	// userId로 유저 검색
	public User getUserbyId(String userId);

	// 프로필 업데이트	
	public boolean updateUserProfile(int id, String url);

	// 프로필 조회	
	public String loadUserProfile(int id);
	
}
