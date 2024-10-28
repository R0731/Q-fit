package com.qfit.mvc.model.service.user;

public interface TrainerService {
	
	public void signUp(int id);
	
	public boolean resign(int id);
	
	public boolean updateGym(int trainerId, String gym);
	
}
