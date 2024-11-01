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

import com.qfit.mvc.model.dto.Review;
import com.qfit.mvc.model.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	/**
	 * 리뷰 읽어오기 메서드
	 * @param questId 읽어올 리뷰의 questId
	 * @return 성공 시 OK (200), 해당 퀘스트 리뷰 없을 시 NO_CONTENT, 실패 시 INTERNAL_SERVER_ERROR 반환
	 */
	@GetMapping("/{questId}")
	public ResponseEntity<?> getReviewById(@PathVariable("questId") int questId) {
		try {
			Review review = reviewService.readReview(questId);
			if (review != null) {
				return new ResponseEntity<Review>(review, HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 리뷰 작성 메서드
	 * @param review 등록할 리뷰
	 * @return 성공 시 CREATED (201), 잘못된 데이터 입력시, 이미 리뷰가 등록되어있을 시 BAD_REQUEST(400),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PostMapping("")
	public ResponseEntity<String> writeReview(@RequestBody Review review){
		try {
			reviewService.writeReview(review);
			return new ResponseEntity<String>("", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Invalid review data", HttpStatus.BAD_REQUEST);
		} catch(IllegalStateException e) {
			return new ResponseEntity<String>("A review for this quest has already been submitted", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 /**
	 * 리뷰 삭제 메서드
	 * @param questId 삭제할 리뷰의 questId
	 * @return 성공 시 OK (200), 리뷰 없을 시 NOT_FOUND(404), 실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@DeleteMapping("/{questId}")
	public ResponseEntity<String> removeReview(@PathVariable("questId") int questId) {
		try {
			reviewService.removeReview(questId);
			return new ResponseEntity<String>("Delete complete", HttpStatus.OK);
		} catch (IllegalStateException e) {
			return new ResponseEntity<String>("Review doesn't exist", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 리뷰 수정 메서드
	 * @param questId 수정할 리뷰의 questId, 수정할 난이도 내용 difficulty
	 * @return 성공 시 OK (200), 데이터 잘못 입력 시 BAD_REQUEST(400), 리뷰 없을 시 NOT_FOUND(404),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PutMapping("/{questId}")
	public ResponseEntity<String> modifyReview(@PathVariable("questId") int questId, @RequestBody Review review) {
		try {
			reviewService.modifyReview(questId, review.getDifficulty());
			return new ResponseEntity<String>("", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}