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
	
	// 유저 정보 업데이트
	@Override
	public boolean updateUser(User user) {
		int result = userDao.updateUser(user);
		return result == 1;
	}
	
	// userId 중복 확인
	@Override
	public boolean isUserIdAvailable(String id) {
		int result = userDao.isUserIdAvailable(id);
		return result == 1;
	}

	// userId로 유저 검색
	@Override
	public User getUserbyId(String userId) {
		User user = userDao.selectOnebyId(userId);
		return user;
	}

	// 프로필 업데이트	
	@Override
	public boolean updateUserProfile(int id, String url) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("userImg", url);
		int result = userDao.updateUserProfile(map);
		return result == 1;
	}

	// 프로필 조회	
	@Override
	public String loadUserProfile(int id) {
		String res = userDao.selectImagebyId(id);
		return res;
	}


}
