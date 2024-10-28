package com.qfit.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.user.LoginRequest;
import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.LoginService;
import com.qfit.mvc.model.service.user.UserService;

@RestController
@RequestMapping("/qfit/user")
public class UserRestController {
	
	private final UserService userService;
	private final LoginService loginService;
	
	@Autowired
	public UserRestController(UserService userService, LoginService loginService) {
		this.userService = userService;
		this.loginService = loginService;
	}
	
	@PostMapping("/regist-trainer")
	public ResponseEntity<?> writeTrainer(@RequestBody Trainer trainer){
		trainer.setUserType(1);
		String result = userService.signUp(trainer);
		if("ID already exists".equals(result)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
		}
		return new ResponseEntity<>(trainer, HttpStatus.CREATED);
	}
	
	@PostMapping("/regist-trainee")
	public ResponseEntity<?> writeTrainee(@RequestBody Trainee trainee){
		trainee.setUserType(2);
		String result = userService.signUp(trainee);
		if("ID already exists".equals(result)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
		}
		return new ResponseEntity<>(trainee, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
		try {
			User loginUser = loginService.login(loginRequest.getUserId(), loginRequest.getUserPassword());
			return ResponseEntity.ok("login success");
		}catch (IllegalArgumentException e) {
			//추후 헤더를 통해 main으로 redirect 되도록 함
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed");
			// TODO: handle exception
		}
		
		
	}
}
