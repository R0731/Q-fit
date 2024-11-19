package com.qfit.mvc.model.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.User;

@Mapper
public interface TraineeDao {
	
	// 트레이니 가입 시 트레이니 테이블에 정보 추가 
	public int addTrainee(int id);

	// 트레이니 탈퇴 시 트레이니 테이블에 정보 삭제
	public int deleteTrainee(int id);
	
	// 트레이니 테이블에 트레이너 정보 등록
	public int addTrainerInfo(Map<String, Integer> info);

	// 트레이니 테이블에서 트레이너 정보 삭제
	public int deleteTrainer(int traineeId);

	// 트레이너 아이디로 트레이니 정보 조회
	public List<Trainee> getTrainee(int trainerId);
	
	// 트레이니의 userId로 트레이니 검색
	public User getTraineeByUserId(String userId);

}
