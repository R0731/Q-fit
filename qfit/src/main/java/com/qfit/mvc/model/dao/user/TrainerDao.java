package com.qfit.mvc.model.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.service.user.UserSearchCondition;

@Mapper
public interface TrainerDao extends UserDao{
	
	public void addTrainer(int id);
	
	public int deleteTrainer(int id);
	
	public void readTrainer(int id);
	
	public int addGym(Map<String, Object> info);

	public List<Trainer> trainerSearch(UserSearchCondition condition);
	
}
