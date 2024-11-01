package com.qfit.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.user.LoginRequest;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.LoginService;
import com.qfit.mvc.model.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	private final UserService userService;
	private final LoginService loginService;
	
	@Autowired
	public UserRestController(UserService userService, LoginService loginService) {
		this.userService = userService;
		this.loginService = loginService;
	}
	
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "모든 유저의 로그인을 수행합니다.")
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
	
	@PutMapping("/update/{id}")
	@Operation(summary = "유저 업데이트", description = "유저 정보를 업데이트합니다.")
	public ResponseEntity<String> userUpdate(@PathVariable("id") int id, @RequestBody User user){
		try {
			user.setId(id);
			userService.updateUser(user);
			return ResponseEntity.status(HttpStatus.OK).body("User update success");
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User update failed");
		}
	}
	
}
