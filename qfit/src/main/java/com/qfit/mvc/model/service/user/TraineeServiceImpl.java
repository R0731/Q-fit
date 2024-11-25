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
import com.qfit.mvc.model.dto.user.User;

@Service
public class TraineeServiceImpl implements TraineeService {
	
	private final TraineeDao traineeDao;
	
	@Autowired
	public TraineeServiceImpl(TraineeDao traineeDao) {
		this.traineeDao = traineeDao;
	}
	
	// 트레이니 테이블에 트레이너 정보 추가
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

	@Override
	@Transactional
	public boolean deleteTrainer(int traineeId) {
		try {
			int result = traineeDao.deleteTrainer(traineeId);
			return result == 1;
		}catch(DataAccessException e){
            throw new IllegalArgumentException("Database Error");
		}
	}

	// 트레이너id로 트레이니 리스트 조회
	@Override
	public List<Trainee> getTraineeList(int trainerId) {
		return traineeDao.getTrainee(trainerId);
	}
	
	// 트레이니 userId로 트레이니 검색
	@Override
	public User findTraineeByUserId(String userId) {
		return traineeDao.getTraineeByUserId(userId);
	}

	@Override
	public int getTrainerId(int traineeId) {
		try {
			int result = traineeDao.getTrainerByTrainerId(traineeId);
			return result;
		}catch(DataAccessException e) {
			throw new IllegalArgumentException("Database Error");
		}
	}
	
	@Override
	public String getTrainerNameByTraineeId(int traineeId) {
		try {
			String name = traineeDao.getTrainerNameByTraineeId(traineeId);
			return name;
		} catch (DataAccessException e) {
			throw new IllegalArgumentException("Database Error");
		}
	}

}
