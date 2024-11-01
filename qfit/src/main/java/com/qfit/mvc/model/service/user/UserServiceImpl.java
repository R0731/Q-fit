package com.qfit.mvc.model.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.user.TraineeDao;
import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dao.user.UserDao;
import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;

@Service
public class UserServiceImpl implements UserService{

	private final UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public boolean isUserIdAvailable(String id) {
		int result = userDao.isUserIdAvailable(id);
		return result == 1;
	}

	@Override
	public User getUserbyId(String userId) {
		User user = userDao.selectOnebyId(userId);
		return user;
	}


}
