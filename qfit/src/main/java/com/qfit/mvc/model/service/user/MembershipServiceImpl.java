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
	
	// 회원 가입 
	@Override
	@Transactional
	public String registMember(User user) {
		// userId 중복 확인
		if(userDao.isUserIdAvailable(user.getUserId()) > 0){
			throw new IllegalArgumentException("User ID is already taken");
		}
		// user 테이블에 공통 정보 등록
		userDao.addUser(user);
		int id = user.getId();
		int userType = user.getUserType();
		//유저 타입에 따라 알맞은 테이블에 정보 추가
		if(userType == 1) {
			trainerDao.addTrainer(id);			
		}else if(userType == 2) {
			traineeDao.addTrainee(id);
		}
		return "User regist successfully";
	}

	// 회원 탈퇴
	@Override
	@Transactional
	public boolean removeMember(int id, String userType) {
		int foreignkeyDeleted = 0;
		// 유저 타입에 따라 연관 테이블 정보 삭제
		if(userType.equals("trainer")) {
			foreignkeyDeleted = trainerDao.deleteTrainer(id);
			}else if(userType.equals("trainee")){
				foreignkeyDeleted = traineeDao.deleteTrainee(id);
			}
		// 연관 테이블 정보 삭제 성공 시 user 테이블 정보 삭제
		int userDeleted = 0;
		if(foreignkeyDeleted == 1) userDeleted = userDao.deleteUser(id);
		return userDeleted == 1;
	}

}
