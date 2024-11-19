package com.qfit.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.service.user.TrainerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/trainer")
public class TrainerRestController {
	
	private final TrainerService trainerService;
	
	@Autowired
	public TrainerRestController(TrainerService trainerService) {
		this.trainerService = trainerService;
	}
	
	/**
	 * 트레이너 테이블에 체육관 읽어오기
	 * @param trainerId 체육관을 업데이트할 트레이너의 ID
	 * @param trainer   업데이트할 체육관 정보를 담고 있는 트레이너 객체
	 * @return 성공 시 OK(200), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@GetMapping("/{trainerId}/gym")
	@Operation(summary = "체육관 정보 조회", description = "트레이너의 체육관 정보를 조회합니다.")
	public ResponseEntity<?> updateGym(@PathVariable("trainerId") int trainerId){
		Trainer trainer = trainerService.getGym(trainerId);
		System.out.println(trainer.getId() + trainer.getGym() + "정보조회");
		if(trainer != null) return ResponseEntity.status(HttpStatus.OK).body(trainer);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Failed");
	}
	
	/**
	 * 트레이너 테이블에 체육관 업데이트 메서드
	 * @param trainerId 체육관을 업데이트할 트레이너의 ID
	 * @param trainer   업데이트할 체육관 정보를 담고 있는 트레이너 객체
	 * @return 성공 시 OK(200), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PutMapping("/{trainerId}/add-gym")
	@Operation(summary = "체육관 정보 업데이트", description = "트레이너의 체육관 정보를 업데이트합니다.")
	public ResponseEntity<String> updateGym(@PathVariable("trainerId") int trainerId, @RequestBody Trainer trainer){
		boolean isUpdated = trainerService.updateGym(trainerId, trainer.getGym());
		if(isUpdated) return ResponseEntity.status(HttpStatus.OK).body("Update Success");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Failed");
	}
	
}
