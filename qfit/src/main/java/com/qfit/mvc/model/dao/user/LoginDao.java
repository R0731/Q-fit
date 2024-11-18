package com.qfit.mvc.model.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.User;

@Mapper
public interface LoginDao {
	
	// 패스워드 일치 여부 확인
	public int passwordCheck();

	// user_id, password 로그인
	public Integer loginCheck(Map<String, Object> info);
	
}
