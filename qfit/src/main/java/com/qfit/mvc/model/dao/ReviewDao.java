package com.qfit.mvc.model.dao;

import java.util.Map;

import com.qfit.mvc.model.dto.Review;
import com.qfit.mvc.model.dto.Review.Difficulty;

public interface ReviewDao {
	
	// 리뷰 존재 여부 확인
	public int reviewExists(int questId);
	
	// ID에 해당하는 리뷰 가져오기
	public Review getReviewByQuestId(int questId);
	// Optional을 사용하면 NullPointerException 방지 가능
	// Optional<Review> getReviewByQuestId(int questId);
	
	// 리뷰 등록
	public void insertReview(Review review);
	
	// 리뷰 삭제
	public int deleteReview(int questId);
	
	// 리뷰 수정
	public void updateReview(Map<String, Object> info);
	
}