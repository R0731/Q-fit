package com.qfit.mvc.model.service.user;

import java.util.List;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.User;

public interface TraineeService{

	// 트레이니 테이블에 트레이너 정보 추가
	public boolean addTrainerToTrainee(int traineeId, int trainerId);

	// 트레이너id로 트레이니 리스트 조회
	public boolean deleteTrainer(int traineeId);

	// 트레이니 userId로 트레이니 검색
	public List<Trainee> getTraineeList(int trainerId);
	
	// 트레이니 userId로 트레이니 검색
	public User findTraineeByUserId(String userId);
	
	// traineeId로 trainerId 가져오기	
	public int getTrainerId(int traineeId);

	// 트레이니의 아이디로 트레이너 이름 조회
	public String getTrainerNameByTraineeId(int traineeId);

}
