package com.qfit.mvc.model.dao;

import java.util.Map;

import com.qfit.mvc.model.dto.Quest;

public interface QuestDao {

	// 퀘스트 존재 여부 확인
	public int questExists(int traineeId, String startAt);
	
	// 퀘스트 가져오기
	public Quest getQuestByTraineeId(Map<String, Object> info);
	
	// 퀘스트 등록
	public void insertQuest(Quest quest);
	
	// 퀘스트 삭제
	public int deleteQuest(int questId);
	
	// 퀘스트 수정
	public void updateQuest(Quest quest);
	
}
