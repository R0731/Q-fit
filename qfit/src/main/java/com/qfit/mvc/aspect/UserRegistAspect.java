package com.qfit.mvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.TraineeService;
import com.qfit.mvc.model.service.user.TrainerService;


@Aspect
@Component
public class UserRegistAspect {
	
	private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public UserRegistAspect(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

	//UserService에서 singUp 메서드 실행 후
	@AfterReturning(
			pointcut = "execution(* com.qfit.mvc.model.service.user.UserServiceImpl.signUp(..)) && args(user)", 
			returning="result")
	public void registUserAdditinalInfo(User user, String result) {
		if("User regist successfully".equals(result)) {
			if(user.getUserType() == 1) trainerService.signUp(user.getId());
			if(user.getUserType() == 2) traineeService.signUp(user.getId());
			System.out.println("User successfully registered. Additional logic executed.");
		}
	}
	
}
