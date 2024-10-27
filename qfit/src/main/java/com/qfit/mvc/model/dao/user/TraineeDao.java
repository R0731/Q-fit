package com.qfit.mvc.model.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.Trainee;

@Mapper
public interface TraineeDao extends UserDao {
	
	public void addTrainee(int id);
	
	public int deleteTrainee(int id);
	
	public int addTrainerInfo(Map<String, Integer> info);
	
	public void selectQr();
	
	public void makeQr();
	
}
