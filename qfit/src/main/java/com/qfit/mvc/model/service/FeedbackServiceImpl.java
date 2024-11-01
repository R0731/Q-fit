package com.qfit.mvc.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.FeedbackDao;
import com.qfit.mvc.model.dto.Feedback;


@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	private final FeedbackDao feedbackDao;
	
	public FeedbackServiceImpl(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}
	
	// 피드백 읽어오기
	@Override
	public Feedback readFeedback(int questId) {
		return feedbackDao.getFeedbackByQuestId(questId);
	}
	
	// 피드백 작성
	@Transactional
	@Override
	public void writeFeedback(Feedback feedback) throws IllegalArgumentException, IllegalStateException {
		if (feedback==null || feedback.getQuestId()==0 || feedback.getContent()==null)
			throw new IllegalArgumentException("Invalid review data.");
		if (feedbackDao.feedbackExists(feedback.getQuestId()) >= 1)
			throw new IllegalStateException("A feedback for this quest has already been submitted.");
		feedbackDao.insertFeedback(feedback);
	}

	// 피드백 삭제
	@Transactional
	@Override
	public void removeFeedback(int questId) throws IllegalStateException {
		if (feedbackDao.feedbackExists(questId)==0)
			throw new IllegalStateException("Feedback doesn't exist");
		feedbackDao.deleteFeedback(questId);
	}

	// 피드백 수정
	@Transactional
	@Override
	public void modifyFeedback(int questId, String content) throws IllegalArgumentException, IllegalStateException {
		if (questId==0 || content==null)
			throw new IllegalArgumentException("Invalid feedback data.");
		if (feedbackDao.feedbackExists(questId)==0)
			throw new IllegalStateException("Feedback doesn't exist");
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("questId", questId);
		info.put("content", content);
		
		feedbackDao.updateFeedback(info);
	}

}