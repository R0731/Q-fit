package com.qfit.mvc.model.dto.user;

import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dao.user.UserDao;

public class Trainer extends User {
	
	private String gym; // 체육관 정보

	public Trainer() {
	}
	
	public Trainer(int id) {
		this.id = id;
	}
	
	public String getGym() {
		return gym;
	}

	public void setGym(String gym) {
		this.gym = gym;
	}
	
}
