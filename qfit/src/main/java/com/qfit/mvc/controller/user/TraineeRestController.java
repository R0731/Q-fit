package com.qfit.mvc.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.TraineeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/trainee")
public class TraineeRestController {
	
	private final TraineeService traineeService;
	
	@Autowired
	public TraineeRestController(TraineeService traineeService) {
		this.traineeService = traineeService;
	}
	
	/**
	 * 트레이니 테이블에서 트레이너 정보 업데이트 메서드
	 * @param traineeId 트레이니의 ID
	 * @param trainerId 추가할 트레이너의 ID
	 * @return 트레이너 추가 성공 시 CREATED(201), 실패 시 NOT_FOUND(404) 반환
	 */
	@PutMapping("{traineeId}/add-trainer/{trainerId}")
	@Operation(summary = "트레이너 정보 업데이트", description = "트레이니에게 트레이너 정보를 업데이트합니다.")
	public ResponseEntity<String> addTrainerToTrainee(@PathVariable(value="traineeId") int traineeId, @PathVariable(value="trainerId") int trainerId) {
        boolean isAdded = traineeService.addTrainerToTrainee(traineeId, trainerId);
        if (isAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Trainer added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to add trainer");
        }
    }

	/**
	 * 트레이니 테이블에서 트레이너 정보 삭제 메서드
	 * @param traineeId 트레이너를 삭제할 트레이니의 ID
	 * @return 트레이너 삭제 성공 시 CREATED(201), 실패 시 NOT_FOUND(404) 반환
	 */
	@DeleteMapping("{traineeId}/delete")
	@Operation(summary = "트레이너 정보 삭제", description = "트레이니 테이블에서 트레이너 정보를 삭제합니다.")
	public ResponseEntity<?> deleteTrainer(@PathVariable(value="traineeId") int traineeId, @PathVariable(value="traineeId") int trainerId) {
        boolean result = traineeService.deleteTrainer(traineeId);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Trainer delete successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete trainer");
        }
    }
	
	/**
	 * 트레이니 추가를 위한 검색 메서드
	 * @param traineeId 트레이너를 삭제할 트레이니의 ID
	 * @return 트레이너 삭제 성공 시 CREATED(201), 실패 시 NOT_FOUND(404) 반환
	 */
//	@DeleteMapping("{traineeId}/delete")
//	@Operation(summary = "트레이너 정보 삭제", description = "트레이니 테이블에서 트레이너 정보를 삭제합니다.")
//	public ResponseEntity<?> deleteTrainer(@PathVariable(value="traineeId") int traineeId, @PathVariable(value="traineeId") int trainerId) {
//        boolean result = traineeService.deleteTrainer(traineeId);
//        if (result) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("Trainer delete successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete trainer");
//        }
//    }
//	
}
