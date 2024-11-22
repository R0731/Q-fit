package com.qfit.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.Quest;
import com.qfit.mvc.model.service.QuestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/quest")
@Tag(name="QuestRestful API", description = "퀘스트 CRUD")
public class QuestRestController {
	
	private final QuestService questService;
	
	public QuestRestController(QuestService questService) {
		this.questService = questService;
	}
	
	/**
	 * 퀘스트 읽어오기 메서드
	 * @param traineeId, startAt 읽어올 퀘스트의 traineeId, startAt
	 * @return 성공 시 OK (200), 해당 퀘스트 없을 시 NO_CONTENT, 실패 시 INTERNAL_SERVER_ERROR 반환
	 */
	@GetMapping("")
	@Operation(summary = "당일 Quest 조회", description = "traineeId와 startAt에 해당하는 퀘스트를 가져옵니다.")
	public ResponseEntity<?> getQuestByTraineeId(@RequestParam("traineeId") int traineeId,
		    @RequestParam("startAt") String startAt) {
		System.out.println("실행확인" + traineeId + " " + startAt);
		try {
			Quest quest = questService.readQuest(traineeId, startAt);
			if (quest != null) {
//				System.out.println(quest.toString());
//				System.out.println(quest.getTasks().toString());
				return new ResponseEntity<Quest>(quest, HttpStatus.OK);
			}
//			System.out.println("퀘스트 없음");
			return new ResponseEntity<String>("No Quest", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
//			System.out.println("문제발생 " + e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 퀘스트 생성 메서드
	 * @param Quest 등록할 퀘스트
	 * @return CREATED (201), 잘못된 데이터 입력 시 BAD_REQUEST(400),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PostMapping("/")
	@Operation(summary = "Quest 등록", description = "trainer가 trainee에게 부여하는 퀘스트를 등록합니다. task를 등록하지 않으면 Quest가 등록되지 않습니다.")
	public ResponseEntity<String> insertQuest(@RequestBody Quest quest) {
		try {
            questService.insertQuest(quest);
            return new ResponseEntity<>("Quest created successfully.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
        	return new ResponseEntity<>("Quest is exists", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create quest: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
     * 훈련생들의 퀘스트 달성률 조회 메서드
     * @param trainerId 트레이너 ID
     * @return 훈련생들의 퀘스트 달성률 목록
     */
    @GetMapping("/{trainerId}")
    @Operation(summary = "훈련생 퀘스트 달성률 조회", description = "주어진 트레이너 ID에 해당하는 훈련생들의 퀘스트 달성률을 조회합니다.")
    public ResponseEntity<?> getTraineeQuestCompletionRate(@PathVariable int trainerId, @RequestParam String startAt) {
        try {
            List<Map<String, Object>> completionRates = questService.getTraineeQuestCompletionRate(trainerId, startAt);
            if (completionRates.isEmpty()) {
                return new ResponseEntity<>("No data available for this trainer.", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(completionRates, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid trainer ID: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    /**
     * 훈련생 퀘스트 상태 조회 메서드
     * @return 정상 조회 OK (200), 서버 오류 시 INTERNAL_SERVER_ERROR (500)
     */
    @GetMapping("/{trainerId}/status")
    @Operation(summary = "Trainee Quest 상태 조회", description = "훈련생의 퀘스트 상태를 조회합니다.")
    public ResponseEntity<List<Map<String, Object>>> getTraineeQuestStatuses(@PathVariable int trainerId, @RequestParam String startAt) {
        try {
            // 서비스 레이어에서 퀘스트 상태 가져오기
            List<Map<String, Object>> statuses = questService.getQuestStatuses(trainerId, startAt);
            return new ResponseEntity<>(statuses, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 INTERNAL_SERVER_ERROR 반환
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
