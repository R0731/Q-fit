package com.qfit.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name="UserRestful API", description = "로그인 및 비밀번호 확인, 유저 정보 조회 및 업데이트")
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
			String token = loginService.login(loginRequest.getUserId(), loginRequest.getUserPassword()); // 로그인 시 사용자 정보와 JWT 토큰을 함께 반환
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed");
		}	
	}	
	
	/**
	 * 비밀번호 확인 메서드(공통)
	 * @param loginRequest 유저의 로그인 정보를 담은 객체 (userId, userPassword)
	 * @return 로그인 성공 시 OK(200), 실패 시 UNAUTHORIZED(401) 반환
	 */
	@PostMapping("/password-correct")
	@Operation(summary = "비밀번호 확인", description = "비밀번호를 확인합니다.")
	public ResponseEntity<?> passwordCheck(@RequestBody LoginRequest loginRequest){
		try {
			String userId = loginRequest.getUserId();
			String password = loginRequest.getUserPassword();
			boolean isCorrect = loginService.correctPassword(userId, password);
			return ResponseEntity.status(HttpStatus.OK).body(isCorrect);
		}catch (IllegalArgumentException e) {
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
	public ResponseEntity<?> userUpdate(@PathVariable("id") int id, @RequestBody User user){
		try {
			user.setId(id);
			boolean res = userService.updateUser(user);
			return ResponseEntity.ok(res);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User update failed");
		}
	}
	
	/**
	 * 유저 정보 조회 메서드
	 * @param userId    조회할 유저의 ID
	 * @return 성공 시 OK(200), 실패 시 CONFLICT(409) 반환
	 */
	@GetMapping("/info/{userId}")
	@Operation(summary = "유저 정보 조회", description = "유저의 개인정보를 조회합니다.")
	public ResponseEntity<?> userInfo(@PathVariable("userId") String userId){
		try {
			User user = userService.getUserbyId(userId);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User Not found");
		}
	}
	
	/**
	 * 유저 프로필 사진 업데이트 메서드
	 * @param id    업데이트할 유저의 ID
	 * @param user  업데이트할 유저 정보를 담은 객체
	 * @return 성공 시 OK(200), 실패 시 CONFLICT(409) 반환
	 */
	@PutMapping("/image-update")
	@Operation(summary = "유저 프로필 업데이트", description = "유저 프로필 정보를 업데이트합니다.")
	public ResponseEntity<?> userProfileUpdate(@RequestBody User user){
		try {
			int id = user.getId();
			String url = user.getUserImg(); // 새로 생성된 이미지 주소
			boolean res = userService.updateUserProfile(id, url);
			return ResponseEntity.ok(res);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User Profile update failed");
		}
	}
	
	/**
	 * 유저 프로필 사진 조회 메서드
	 * @param id    조회할 유저의 ID
	 * @return 성공 시 OK(200), 실패 시 CONFLICT(409) 반환
	 */
	@GetMapping("/image-load/{id}")
	@Operation(summary = "유저 프로필 조회", description = "유저 프로필 정보를 조회합니다.")
	public ResponseEntity<?> userProfileLoad(@PathVariable("id") int id){
		try {
			String res = userService.loadUserProfile(id); // 해당하는 유저의 이미지 주소 정보 반환
			return ResponseEntity.ok(res);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User Profile load failed");
		}
	}
}
