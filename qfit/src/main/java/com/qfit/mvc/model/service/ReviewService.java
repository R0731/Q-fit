package com.qfit.mvc.model.service;

import com.qfit.mvc.model.dto.Review;

public interface ReviewService {
	
	// 리뷰 읽어오기
	public Review readReview(int questId);
	
	// 리뷰 작성
	public void writeReview(Review review) throws IllegalArgumentException;
	
	// 리뷰 삭제
	public boolean removeReview(int questId);
	
	// 리뷰 수정
	public void modifyReview(Review review) throws IllegalArgumentException;
	
}