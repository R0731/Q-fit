package com.qfit.mvc.model.service;

import java.util.List;
import java.util.Map;

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
	
	// 피드백이 없는 퀘스트 목록을 반환
    public List<Map<String, Object>> getFeedbackPendingQuests(int trainerId);
    
}