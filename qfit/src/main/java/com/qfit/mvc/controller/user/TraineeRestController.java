package com.qfit.mvc.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.TraineeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/trainee")
@Tag(name="TraineeRestful API", description = "트레이니 검색, 트레이너 추가 및 삭제, 트레이너에 해당하는 트레이니 리스트 조회")
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
	 * 트레이너의 트레이니 조회 메서드
	 * @param trainerId 조회할 트레이너의 ID
	 * @return 성공 시 OK(200), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@GetMapping("/{trainerId}/trainee-list")
	@Operation(summary = "trainee리스트 조회", description = "트레이너의 id를 기반으로 트레이니를 불러옵니다.")
	public ResponseEntity<?> traineeList(@PathVariable("trainerId") int trainerId){
		try {
			List<Trainee> list = traineeService.getTraineeList(trainerId);
			if(list.size() < 1) return ResponseEntity.status(HttpStatus.OK).body(null);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");			
		}
	}
	
	/**
	 * 트레이니의 트레이너 조회 메서드
	 * @param traineeId 조회할 트레이니의 ID
	 * @return 성공 시 OK(200), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@GetMapping("/{traineeId}/read-trainer")
	@Operation(summary = "trainerId조회", description = "트레이니의 id를 기반으로 트레이너 id를 찾습니다.")
	public ResponseEntity<?> readTrainerId(@PathVariable("traineeId") int traineeId){
		try {
			int trainerId = traineeService.getTrainerId(traineeId);
			return ResponseEntity.status(HttpStatus.OK).body(trainerId);
		}catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");			
		}
	}

	/**
	 * 트레이니 추가를 위한 검색 메서드
	 * @param userId 검색할 트레이니의 userId
	 * @return 트레이니 검색 성공 시 OK(200), 조회결과 없을 시 NOT_FOUND(404), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@GetMapping("/search-trainee")
	@Operation(summary = "트레이니 검색", description = "추가할 트레이니 정보를 검색합니다.")
	public ResponseEntity<?> searchTrainee(@RequestParam String userId) {
        try {
        	User trainee = traineeService.findTraineeByUserId(userId);
        	if (trainee != null) {
        		return ResponseEntity.ok(trainee);
        	}
        	else {
        		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee not found or already assigned to a trainer.");
        	}
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }
    }
	
	/**
     * 트레이너 이름을 가져오는 검색 메서드
     * @param traineeId 트레이니의 ID
     * @return 트레이너 이름 조회 성공 시 OK(200), 트레이너 이름이 없을 시 NOT_FOUND(404), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
     */
    @GetMapping("/search-trainer")
    @Operation(summary = "트레이너 이름 검색", description = "트레이니 ID를 통해 트레이너의 이름을 검색합니다.")
    public ResponseEntity<?> searchTrainer(@RequestParam int traineeId) {
        try {
            String trainerName = traineeService.getTrainerNameByTraineeId(traineeId);
            if (trainerName != null && !trainerName.isEmpty()) {
                return ResponseEntity.ok(trainerName);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer not found for the given trainee.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve trainer name.");
        }
    }
    
}
