package com.qfit.mvc.model.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.User;

@Mapper
public interface LoginDao {

	public int passwordCheck();

	public Integer loginCheck(Map<String, Object> info);
	
}
