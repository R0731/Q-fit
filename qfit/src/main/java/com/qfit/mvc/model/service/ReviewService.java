package com.qfit.mvc.model.service;

import com.qfit.mvc.model.dto.Review;
import com.qfit.mvc.model.dto.Review.Difficulty;

public interface ReviewService {
	
	// 리뷰 읽어오기
	public Review readReview(int questId);
	
	// 리뷰 작성
	public void writeReview(Review review) throws IllegalArgumentException, IllegalStateException;
	
	// 리뷰 삭제
	public void removeReview(int questId) throws IllegalStateException;
	
	// 리뷰 수정
	public void modifyReview(int questId, Difficulty difficulty) throws IllegalArgumentException, IllegalStateException;
	
}