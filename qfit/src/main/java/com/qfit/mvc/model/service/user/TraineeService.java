package com.qfit.mvc.model.service.user;

import java.util.List;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.User;

public interface TraineeService{

	public boolean addTrainerToTrainee(int traineeId, int trainerId);

	public boolean deleteTrainer(int traineeId);

	public List<Trainee> getTraineeList(int trainerId);
	
	public User findTraineeByUserId(String userId);
	
	public int getTrainerId(int traineeId);

}
