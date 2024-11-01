package com.qfit.mvc.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.ReviewDao;
import com.qfit.mvc.model.dto.Review;
import com.qfit.mvc.model.dto.Review.Difficulty;

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
	@Transactional
	@Override
	public void writeReview(Review review) {
		if (review==null || review.getQuestId()==0 || review.getDifficulty()==null)
			throw new IllegalArgumentException("Invalid review data.");
		if (reviewDao.reviewExists(review.getQuestId()) >= 1)
			throw new IllegalStateException("A review for this quest has already been submitted.");
		reviewDao.insertReview(review);
	}

	// 리뷰 삭제
	@Transactional
	@Override
	public void removeReview(int questId) {
		if (reviewDao.reviewExists(questId) == 0)
			throw new IllegalStateException("Review doesn't exist");
		reviewDao.deleteReview(questId);
	}

	// 리뷰 수정
	@Transactional
	@Override
	public void modifyReview(int questId, Difficulty difficulty) {
		if (difficulty==null || questId==0)
			throw new IllegalArgumentException("Invalid review data.");
		if (reviewDao.reviewExists(questId) == 0)
			throw new IllegalStateException("Review doesn't exist");
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("questId", questId);
		info.put("difficulty", difficulty);
		
		reviewDao.updateReview(info);
	}

}