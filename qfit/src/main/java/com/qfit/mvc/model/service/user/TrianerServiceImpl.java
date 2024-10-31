package com.qfit.mvc.model.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.user.TrainerDao;

@Service
public class TrianerServiceImpl implements TrainerService{
	
	private final TrainerDao trainerDao;
	
	@Autowired
	public TrianerServiceImpl(TrainerDao trainerDao){
		this.trainerDao = trainerDao;
	}
	
	@Override
	public void signUp(int id) {
		trainerDao.addTrainer(id);
	}
	
	@Override
	@Transactional
	public boolean resign(int id) {
		int result1 = trainerDao.deleteTrainer(id);
		int result2 = trainerDao.deleteUser(id);
		
		return result1 == 1 && result2 == 1;
	}
	
	@Override
	public boolean updateGym(int trainerId, String gym) {
		Map<String, Object> temp = new HashMap<>();
		temp.put("id", trainerId);
		temp.put("gym", gym);
		int result = trainerDao.addGym(temp);
		return result == 1;
	}




}
