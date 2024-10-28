package com.qfit.mvc.model.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.user.TraineeDao;
import com.qfit.mvc.model.dto.user.Trainee;

@Service
public class TraineeServiceImpl implements TraineeService {
	
	private final TraineeDao traineeDao;
	
	@Autowired
	public TraineeServiceImpl(TraineeDao traineeDao) {
		this.traineeDao = traineeDao;
	}
	
	@Override
	public void signUp(int id) {
		traineeDao.addTrainee(id);
	}
	
	@Override
	@Transactional
	public boolean resign(int id) {
		int result1 = traineeDao.deleteTrainee(id);
		int result2 = traineeDao.deleteUser(id);
		
		return result1 == 1 && result2 == 1;
	}
	
	@Override
	@Transactional
	public boolean updateTrainer(int traineeId, int trainerId) {
		Map<String, Integer> temp = new HashMap<>();
		temp.put("traineeId", traineeId);
		temp.put("trainerId", trainerId);
		int result = traineeDao.addTrainerInfo(temp);
		return result == 1;
	}



}
