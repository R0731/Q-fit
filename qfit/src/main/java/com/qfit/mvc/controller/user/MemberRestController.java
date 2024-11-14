package com.qfit.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.qfit.mvc.model.service.user.MembershipService;
import com.qfit.mvc.model.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/member")
public class MemberRestController {
	
	private final MembershipService membershipService;
	
	@Autowired
	public MemberRestController(MembershipService membershipService) {
		this.membershipService = membershipService;
	}
	
	/**
	 * userId 중복체크 메서드
	 * @param userId 유효성 검사할 userId(Request Body로 전달)
	 * @return 성공 시 true or false, 서버 에러시(SERVER_ERROR) 반환
	 */
	@PostMapping("/idCheck")
	@Operation(summary = "userId 중복 체크", description = "회원가입 중 userId 중복 체크")
	public ResponseEntity<?> idCheck(@RequestBody String userId){
		try {
			boolean isAvailable = membershipService.idCheck(userId);
			return ResponseEntity.ok(isAvailable);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	/**
	 * 새로운 유저 가입 메서드
	 * @param userType 유저의 타입 (trainer 또는 trainee)
	 * @param user     등록할 유저의 정보 (Request Body로 전달)
	 * @return 성공 시 CREATED(201), 유저 타입이 잘못되었을 경우 CONFLICT(409) 반환
	 */
	@PostMapping("/{userType}/regist")
	@Operation(summary = "새로운 유저 가입", description = "유저 타입에 따라 유저 정보를 등록합니다.")
	public ResponseEntity<?> userRegist(@PathVariable("userType") String userType, @RequestBody User user){
		try {
			membershipService.registMember(user, userType);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	/**
	 * 유저 탈퇴 메서드
	 * @param userType 유저의 타입 (trainer 또는 trainee)
	 * @param id       탈퇴할 유저의 ID
	 * @return 성공 시 OK(200), 실패 시 CONFLICT(409) 반환
	 */
	@DeleteMapping("/{userType}/resign/{id}")
	@Operation(summary = "유저 탈퇴", description = "유저 타입에 따라 유저 정보를 탈퇴 처리합니다.")
	public ResponseEntity<?> userResign(@PathVariable(value="userType") int userType, @PathVariable(value="id") int id){
		boolean result = false;
		result = membershipService.removeMember(id, userType);
		if(!result) return ResponseEntity.status(HttpStatus.CONFLICT).body("Delete Failed");
		return ResponseEntity.status(HttpStatus.OK).body("Delete Completed");
	}
}
