package com.qfit.mvc.model.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qfit.mvc.model.dao.user.TraineeDao;
import com.qfit.mvc.model.dao.user.UserDao;

public class Trainee extends User{
	
	private int trainerId; // 트레이너 고유 ID
	
    public Trainee(int id) {
        this.id = id;
    }
	
	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}
}
