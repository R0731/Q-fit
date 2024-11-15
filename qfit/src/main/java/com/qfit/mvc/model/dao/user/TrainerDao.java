package com.qfit.mvc.model.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.service.user.UserSearchCondition;

@Mapper
public interface TrainerDao extends UserDao{
	
	// 트레이너 가입 시 트레이너 테이블에 정보 추가 
	public void addTrainer(int id);
	
	// 트레이너 탈퇴 시 트레이너 테이블에 정보 삭제
	public int deleteTrainer(int id);
	
	// 트레이너 체육관 정보 업데이트
	public int addGym(Map<String, Object> info);

	// 트레이너 검색
	public List<Trainer> trainerSearch(UserSearchCondition condition);

	public List<Trainee> getTrainee(int trainerId);
	
}
