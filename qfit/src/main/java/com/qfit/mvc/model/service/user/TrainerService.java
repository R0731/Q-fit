package com.qfit.mvc.model.service.user;

import java.util.List;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;

public interface TrainerService {
		
	public boolean updateGym(int trainerId, String gym);

	public List<Trainee> getTraineeList(int trainerId);
	
//	public List<Trainer> getTrainerSearch(UserSearchCondition conditon);
	
}
