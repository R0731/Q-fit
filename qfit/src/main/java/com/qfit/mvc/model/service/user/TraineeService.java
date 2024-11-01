package com.qfit.mvc.model.service.user;

import java.util.List;
import java.util.Map;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;

public interface TraineeService{

	public boolean addTrainerToTrainee(int traineeId, int trainerId);

}
