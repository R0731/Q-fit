package com.qfit.mvc.model.dao;

import com.qfit.mvc.model.dto.Review;

public interface ReviewDao {
	// 퀘스트 아이디로 가져오는 게 맞을 거 같은데 리뷰 아이디가 맞는지?
	// ID에 해당하는 리뷰 가져오기
	public Review getReviewByQuestId(int questId);
	// Optional<Review> getReviewByQuestId(int questId);
	// Optional을 사용하면 NullPointerException 방지 가능
	
	// 리뷰 등록
	public void insertReview(Review review);
	
	// 리뷰 삭제
	public int deleteReview(int reviewId);
	
	// 리뷰 수정
	public void updateReview(Review review);
	
	// 조회수 증가도 필요 없겠지?
	// 검색 기능도 필요 없겠지?
}