package com.qfit.mvc.model.service.user;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;

public interface MembershipService {
	
	// 아이디 중복 체크
	public boolean idCheck(String userId);
	
	// 회원 가입
	public String registMember(User user, String userType);
	
	// 회원 삭제
    public boolean removeMember(int id, int userType);                 
    
}
