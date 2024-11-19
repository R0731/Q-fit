package com.qfit.mvc.model.service.user;

import java.util.List;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;

public interface TrainerService {
		
	public Trainer getGym(int trainerId);

	public boolean updateGym(int trainerId, String gym);

	
//	public List<Trainer> getTrainerSearch(UserSearchCondition conditon);
	
}
