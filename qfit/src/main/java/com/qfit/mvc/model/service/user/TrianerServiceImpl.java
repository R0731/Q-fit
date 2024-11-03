package com.qfit.mvc.model.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dto.user.Trainer;

@Service
public class TrianerServiceImpl implements TrainerService{
	
	private final TrainerDao trainerDao;
	
	@Autowired
	public TrianerServiceImpl(TrainerDao trainerDao){
		this.trainerDao = trainerDao;
	}
	
	// 트레이너의 체육관 정보 업데이트
	@Override
	public boolean updateGym(int trainerId, String gym) {
		Map<String, Object> temp = new HashMap<>();
		temp.put("id", trainerId);
		temp.put("gym", gym);
		int result = trainerDao.addGym(temp);
		return result == 1;
	}

//	@Override
//	public List<Trainer> getTrainerSearch(UserSearchCondition condition) {
//		return trainerDao.trainerSearch(condition);
//	}




}
