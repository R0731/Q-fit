package com.qfit.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.qfit.mvc.model.dto.Feedback;

public interface FeedbackDao {
	
	// 피드백 존재 여부 확인
	public int feedbackExists(int questId);
	
	// ID에 해당하는 피드백 가져오기
	public Feedback getFeedbackByQuestId(int questId);
	
	// 피드백 등록
	public void insertFeedback(Feedback feedback);
	
	// 피드백 삭제
	public int deleteFeedback(int questId);
	
	// 피드백 수정
	public void updateFeedback(Map<String, Object> info);
	
	// 피드백이 없는 퀘스트 목록을 반환
    public List<Map<String, Object>> getFeedbackPendingQuests(int trainerId);
    
}