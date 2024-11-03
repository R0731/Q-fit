package com.qfit.mvc.controller;

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

import com.qfit.mvc.model.dto.Feedback;
import com.qfit.mvc.model.service.FeedbackService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/feedback")
@Tag(name="FeedbackRestful API", description = "피드백 CRUD")
public class FeedbackController {
	
	private final FeedbackService feedbackService;
	
	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	
	/**
	 * 피드백 읽어오기 메서드
	 * @param questId 읽어올 피드백의 questId
	 * @return 성공 시 OK (200), 해당 퀘스트 피드백 없을 시 NO_CONTENT, 실패 시 INTERNAL_SERVER_ERROR 반환
	 */
	@GetMapping("/{questId}")
	@Operation(summary = "피드백 조회", description = "questId에 해당하는 피드백을 가져옵니다. 등록된 피드백이 있다면, 등록이 되지 않도록 하여 퀘스트 하나 당 피드백 하나만 등록될 수 있도록 합니다.")
	public ResponseEntity<?> getFeedbackById(@PathVariable("questId") int questId) {
		try {
			Feedback feedback = feedbackService.readFeedback(questId);
			if (feedback != null) {
				return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
			}
			return new ResponseEntity<String>("No Feedback", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 피드백 작성 메서드
	 * @param feedback 등록할 피드백
	 * @return 성공 시 CREATED (201), 잘못된 데이터 입력시, 이미 피드백이 등록되어있을 시 BAD_REQUEST(400),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PostMapping("")
	@Operation(summary = "피드백 작성", description = "작성된 피드백이 없을 시 피드백을 등록합니다.")
	public ResponseEntity<String> writeFeedback(@RequestBody Feedback feedback){
		try {
			feedbackService.writeFeedback(feedback);
			return new ResponseEntity<String>("", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Invalid feedback data", HttpStatus.BAD_REQUEST);
		} catch(IllegalStateException e) {
			return new ResponseEntity<String>("A feedback for this quest has already been submitted", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 /**
	 * 피드백 삭제 메서드
	 * @param questId 삭제할 피드백의 questId
	 * @return 성공 시 OK (200), 피드백 없을 시 NOT_FOUND(404), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@DeleteMapping("/{questId}")
	@Operation(summary = "피드백 삭제", description = "questId에 해당하는 피드백을 삭제합니다.")
	public ResponseEntity<String> removeFeedback(@PathVariable("questId") int questId) {
		try {
			feedbackService.removeFeedback(questId);
			return new ResponseEntity<String>("Delete complete", HttpStatus.OK);
		} catch (IllegalStateException e) {
			return new ResponseEntity<String>("Feedback doesn't exist", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 피드백 수정 메서드
	 * @param questId 수정할 피드백의 questId, 수정할 난이도 내용 difficulty
	 * @return 성공 시 OK (200), 데이터 잘못 입력 시 BAD_REQUEST(400), 피드백 없을 시 NOT_FOUND(404),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PutMapping("/{questId}")
	@Operation(summary = "피드백 수정", description = "questId에 해당하는 피드백 내용을 수정합니다. 피드백 내용만 수정 가능하도록 만들어져 있습니다.")
	public ResponseEntity<String> modifyFeedback(@PathVariable("questId") int questId, @RequestBody Feedback feedback) {
		try {
			feedbackService.modifyFeedback(questId, feedback.getContent());
			return new ResponseEntity<String>("", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}