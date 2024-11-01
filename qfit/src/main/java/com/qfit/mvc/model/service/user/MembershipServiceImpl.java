package com.qfit.mvc.model.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.user.TraineeDao;
import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dao.user.UserDao;
import com.qfit.mvc.model.dto.user.User;

@Service
public class MembershipServiceImpl implements MembershipService{

	private final UserDao userDao;
	private final TrainerDao trainerDao;
	private final TraineeDao traineeDao;
	
	@Autowired
	public MembershipServiceImpl(UserDao userDao, TrainerDao trainerDao, TraineeDao traineeDao) {
		this.userDao = userDao;
		this.trainerDao = trainerDao;
		this.traineeDao = traineeDao;
	}
	
	@Override
	@Transactional
	public String registMember(User user) {
		if(userDao.isUserIdAvailable(user.getUserId()) > 0){
			throw new IllegalArgumentException("User ID is already taken");
		}
		userDao.addUser(user);
		int id = user.getId();
		int userType = user.getUserType();
		if(userType == 1) {
			trainerDao.addTrainer(id);			
		}else if(userType == 2) {
			traineeDao.addTrainee(id);
		}
		return "User regist successfully";
	}

	@Override
	@Transactional
	public boolean removeMember(int id, String userType) {
		int foreignkeyDeleted = 0;
		if(userType.equals("trainer")) {
			foreignkeyDeleted = trainerDao.deleteTrainer(id);
			}else if(userType.equals("trainee")){
				foreignkeyDeleted = traineeDao.deleteTrainee(id);
			}
		int userDeleted = userDao.deleteUser(id);
		return userDeleted == 1;
	}

}
