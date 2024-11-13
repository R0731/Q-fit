package com.qfit.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.Quest;
import com.qfit.mvc.model.service.QuestService;

@RestController
@RequestMapping("/quest")
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
	@GetMapping("/")
	public ResponseEntity<?> getQuestByTraineeId(@RequestParam("traineeId") int traineeId,
		    @RequestParam("startAt") String startAt) {
		try {
			Quest quest = questService.readQuest(traineeId, startAt);
			if (quest != null) {
				return new ResponseEntity<Quest>(quest, HttpStatus.OK);
			}
			return new ResponseEntity<String>("No Quest", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
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
	public ResponseEntity<String> insertQuest(@RequestBody Quest quest) {
		try {
            questService.insertQuest(quest);
            return new ResponseEntity<>("Quest created successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create quest: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
