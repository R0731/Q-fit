package com.qfit.mvc.model.factory;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.dto.user.UserDto;

public class UserFactory {
	
	public static User createUserByType(UserDto userdto) {
		switch(userdto.getUserType()) {
		case 1:
			return new Trainer(userdto);
		case 2:
			return new Trainee(userdto);
		default:
			throw new IllegalArgumentException("Invalid userType: " + userdto.getUserType());
		}
	}
}
