package com.qfit.mvc.model.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.dto.user.UserDto;

@Mapper
public interface UserDao {
	
	public void addUser(User user);
	
	public int deleteUser(int id);
	
	public void readUser(User user);
	
	public UserDto selectOnebyId(String userId); 
		
	public void updatePassword(String password);
	
	public void updateUser(User user);
	
	public int isUserIdAvailable(String userId);
	
	
}
