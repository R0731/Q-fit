package com.qfit.mvc.model.dto.user;

import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dao.user.UserDao;

public class Trainer extends User {
	
	private String gym;

	public Trainer() {
		super();
	}
	
	public Trainer(int id) {
		this.id = id;
	}
	
	public Trainer(User user) {
		// TODO Auto-generated constructor stub
	}

	public Trainer(UserDto userdto) {
		// TODO Auto-generated constructor stub
	}

	public String getGym() {
		return gym;
	}

	public void setGym(String gym) {
		this.gym = gym;
	}
	
}
