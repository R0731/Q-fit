package com.qfit.mvc.model.service;

import com.qfit.mvc.model.dto.Review;

public interface ReviewService {
	
	// 리뷰 읽어오기
	/**
	 * 이 메서드는 리뷰를 Dao로부터 읽어와서 반환해줍니다...
	 */
	public Review readReview(int questId);
	
	// 리뷰 작성
	public void writeReview(Review review) throws IllegalArgumentException;
	
	// 리뷰 삭제
	public boolean removeReview(int questId);
	
	// 리뷰 수정
	public void modifyReview(Review review) throws IllegalArgumentException;
	
}