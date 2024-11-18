package com.qfit.mvc.model.service;

import com.qfit.mvc.model.dto.Feedback;

public interface FeedbackService {
	
	// 리뷰 읽어오기
	public Feedback readFeedback(int questId);
	
	// 리뷰 작성
	public void writeFeedback(Feedback feedback) throws IllegalArgumentException, IllegalStateException;
	
	// 리뷰 삭제
	public void removeFeedback(int questId) throws IllegalStateException;
	
	// 리뷰 수정
	public void modifyFeedback(int questId, String content) throws IllegalArgumentException, IllegalStateException;
	
}