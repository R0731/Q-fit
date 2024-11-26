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
	List<Map<String, Object>> getTraineeQuestCompletionRate(int traineeId, String startAt, String endAt);
	
	// 퀘스트 상태 출력
	public List<Map<String, Object>> getQuestStatuses(int trainerId, String startAt);

}
