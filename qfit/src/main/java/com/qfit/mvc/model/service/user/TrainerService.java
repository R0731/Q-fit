package com.qfit.mvc.model.service.user;

import java.util.List;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;

public interface TrainerService {
		
	// 체육관 정보 조회
	public Trainer getGym(int trainerId);

	// 체육관 정보 업데이트
	public boolean updateGym(int trainerId, String gym);
}
