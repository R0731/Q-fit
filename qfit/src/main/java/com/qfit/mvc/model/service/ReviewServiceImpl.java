package com.qfit.mvc.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.ReviewDao;
import com.qfit.mvc.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewDao reviewDao;
	
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}
	
	// 리뷰 읽어오기
	@Override
	public Review readReview(int questId) {
		return reviewDao.getReviewByQuestId(questId);
	}

	// 리뷰 작성
	@Transactional // 트랜잭션 처리
	@Override
	public void writeReview(Review review) {
		if (review==null || review.getQuestId()==0 || review.getDifficulty()==null)
			throw new IllegalArgumentException("Invalid review data.");
		reviewDao.insertReview(review);
	}

	// 리뷰 삭제
	@Transactional
	@Override
	public boolean removeReview(int questId) {
		int result = reviewDao.deleteReview(questId);
		return result==1;
	}

	// 리뷰 수정
	@Transactional
	@Override
	public void modifyReview(Review review) {
		if (review==null || review.getReviewId()==0)
			throw new IllegalArgumentException("Invalid review data.");
		reviewDao.updateReview(review);
	}

}