package com.qfit.mvc.model.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;

@Mapper
public interface UserDao {
	
	// 유저 가입 시 유저 테이블에 정보 추가 
	public void addUser(User user);
	
	// 유저 탈퇴 시 유저 테이블에 정보 삭제 
	public int deleteUser(int id);
	
//	public void readUser(User user);
	
	// user_id로 회원조회
	public User selectOnebyId(String userId); 
	
	// 유저 정보 업데이트
	public int updateUser(User user);
	
	// user_id 중복 확인
	public int isUserIdAvailable(String userId);
	
	
}
