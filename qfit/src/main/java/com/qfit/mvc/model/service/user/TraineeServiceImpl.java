package com.qfit.mvc.model.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.user.TraineeDao;
import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;

@Service
public class TraineeServiceImpl implements TraineeService {
	
	private final TraineeDao traineeDao;
	
	@Autowired
	public TraineeServiceImpl(TraineeDao traineeDao) {
		this.traineeDao = traineeDao;
	}

	@Override
	@Transactional
	public boolean addTrainerToTrainee(int traineeId, int trainerId) {
		Map<String, Integer> temp = new HashMap<>();
		temp.put("traineeId", traineeId);
		temp.put("trainerId", trainerId);
		try {
	        int result = traineeDao.addTrainerInfo(temp);
	        return result == 1;
	    } catch (DataAccessException e) {
            throw new IllegalArgumentException("Database Error");
	    }
    }

}
