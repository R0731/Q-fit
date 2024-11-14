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
	public String registMember(User user, String userType) {
		// userId 중복 확인
		if(userDao.isUserIdAvailable(user.getUserId()) > 0){
			throw new IllegalArgumentException("User ID is already taken");
		}
		// user 테이블에 공통 정보 등록
		//유저 타입에 따라 알맞은 테이블에 정보 추가
		int id;
		switch(userType) {
			case "trainer":
				user.setUserType(1);
				userDao.addUser(user);
				id = user.getId();
				trainerDao.addTrainer(id);
				break;
			case "trainee":
				user.setUserType(2);
				userDao.addUser(user);
				id = user.getId();
				traineeDao.addTrainee(id);
				break;
			default:
				return "존재하지 않는 유저 타입입니다.";
		}
		return "User regist successfully";
	}

	// 회원 탈퇴
	@Override
	@Transactional
	public boolean removeMember(int id, int userType) {
		int foreignkeyDeleted = 0;
		// 유저 타입에 따라 연관 테이블 정보 삭제
		switch(userType) {
			case 1: // trainer
				foreignkeyDeleted = trainerDao.deleteTrainer(id);
				break;
			case 2: // trainee
				foreignkeyDeleted = traineeDao.deleteTrainee(id);
				break;
			default:
				System.out.println("존재하지 않는 유저 타입입니다.");
				return false;
		}		
		// 연관 테이블 정보 삭제 성공 시 user 테이블 정보 삭제
		int userDeleted = 0;
		if(foreignkeyDeleted == 1) userDeleted = userDao.deleteUser(id);
		return userDeleted == 1;
	}

	// 왜 유저 중복 체크를 따로 빼니까 다 true로 뜨지???
	// 유저 아이디 중복 체크
	@Override
	public boolean idCheck(String userId) {
		System.out.println("유저id" + userId);
		if(userDao.isUserIdAvailable(userId) > 0){
			System.out.println(userDao.isUserIdAvailable(userId) + "거짓");
			return false;
		}
		System.out.println(userDao.isUserIdAvailable(userId) + "진실");

		return true;
	}

}
