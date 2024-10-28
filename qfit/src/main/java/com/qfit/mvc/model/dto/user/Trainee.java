package com.qfit.mvc.model.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qfit.mvc.model.dao.user.TraineeDao;
import com.qfit.mvc.model.dao.user.UserDao;

public class Trainee extends User{
	
	private int trainerId;
	
	public Trainee(UserDto userdto) {
		super();
	}
	
    public Trainee(int id) {
        this.id = id;
    }
	
	public Trainee(User user) {
		// TODO Auto-generated constructor stub
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}
}
