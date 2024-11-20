package com.qfit.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.qfit.mvc.model.dto.Quest;

public interface QuestService {

	// 퀘스트 읽어오기
	public Quest readQuest(int traineeId, String starAt);
	
	// 퀘스트 생성
	public void insertQuest(Quest quest);

	// 퀘스트 수행률 계산
	List<Map<String, Object>> getTraineeQuestCompletionRate(int trainerId, String startAt);
	
	// 퀘스트 삭제 만들면 안될거같음...
	
	// 퀘스트 수정 추후 필요하면 만들기!
	
}
