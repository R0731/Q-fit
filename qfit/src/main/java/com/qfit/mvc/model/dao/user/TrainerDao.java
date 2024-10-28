package com.qfit.mvc.model.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainerDao extends UserDao{
	
	public void addTrainer(int id);
	
	public int deleteTrainer(int id);
	
	public void readTrainer(int id);
	
	public int addGym(Map<String, Object> info);
	
}
