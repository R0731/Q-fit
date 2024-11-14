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
	
	/**
	 * 로그인 메서드(공통)
	 * @param loginRequest 유저의 로그인 정보를 담은 객체 (userId, userPassword)
	 * @return 로그인 성공 시 OK(200), 실패 시 UNAUTHORIZED(401) 반환
	 */
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "모든 유저의 로그인을 수행합니다.")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		try {
			// 로그인 시 사용자 정보와 JWT 토큰을 함께 반환
			String token = loginService.login(loginRequest.getUserId(), loginRequest.getUserPassword());
//			User loginUser = loginService.login(loginRequest.getUserId(), loginRequest.getUserPassword());
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}catch (IllegalArgumentException e) {
			//추후 헤더를 통해 main으로 redirect 되도록 할 예정
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed");
		}	
	}	
	
	/**
	 * 유저 정보 업데이트 메서드
	 * @param id    업데이트할 유저의 ID
	 * @param user  업데이트할 유저 정보를 담은 객체
	 * @return 성공 시 OK(200), 실패 시 CONFLICT(409) 반환
	 */
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
