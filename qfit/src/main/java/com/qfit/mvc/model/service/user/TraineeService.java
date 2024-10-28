package com.qfit.mvc.model.service.user;

import java.util.Map;

import com.qfit.mvc.model.dto.user.Trainee;

public interface TraineeService {
	
	public void signUp(int id);
	
	public boolean resign(int id);
	
	public boolean updateTrainer(int traineeId, int trainerId);


}
