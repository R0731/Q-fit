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
@RequestMapping("/reviews")
public class ReviewController {
	
	// 서비스 의존성 주입
	private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	// 리뷰 읽어오기
	@GetMapping("/{questId}")
	public ResponseEntity<?> getReviewById(@PathVariable("questId") int questId) {
		System.out.println(questId);
		try {
			Review review = reviewService.readReview(questId);
			System.out.println(review);
			if (review != null) {
				return new ResponseEntity<Review>(review, HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 리뷰 작성
	@PostMapping("")
	public ResponseEntity<String> writeReview(@RequestBody Review review){
		try {
			reviewService.writeReview(review);
			return new ResponseEntity<String>("", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Invalid review data", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 /**
	 * 리뷰 삭제 메서드
	 * @param questId 삭제할 리뷰의 questId
	 * @return 성공 시 OK (200), 실패 시 INTERNAL_SERVER_ERROR 반환
	 */
	@DeleteMapping("/{questId}")
	public ResponseEntity<String> removeReview(@PathVariable("questId") int questId) {
		try {
			if (reviewService.removeReview(questId)) {
				return new ResponseEntity<String>("삭제되었습니다.", HttpStatus.OK);
			}
			return new ResponseEntity<String>("실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 리뷰 수정
	@PutMapping("/{questId}")
	public ResponseEntity<String> modifyReview(@PathVariable int questId, @RequestBody Review review) {
		try {
			
			reviewService.modifyReview(review);
			return new ResponseEntity<String>("", HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}