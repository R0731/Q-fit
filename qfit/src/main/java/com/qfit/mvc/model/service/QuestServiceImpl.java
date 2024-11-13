package com.qfit.mvc.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfit.mvc.model.dao.QuestDao;
import com.qfit.mvc.model.dto.Quest;
import com.qfit.mvc.model.dto.Task;

@Service
public class QuestServiceImpl implements QuestService {

	private final QuestDao questDao;
	private final TaskService taskService;
	// 만약에 삭제 넣을거면 필요해서 생성해둔 review, feedbackService
	private final ReviewService reviewService;
	private final FeedbackService feedbackService;
	
	public QuestServiceImpl(QuestDao questDao, TaskService taskService, ReviewService reviewService, FeedbackService feedbackService) {
		this.questDao = questDao;
		this.taskService = taskService;
		this.reviewService = reviewService;
		this.feedbackService = feedbackService;
	}
	
	// 퀘스트 가져오기
	@Override
	public Quest readQuest(int traineeId, String startAt) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("traineeId", traineeId);
		info.put("startAt", startAt);
		return questDao.getQuestByTraineeId(info);
	}

	// 퀘스트 생성
	@Override
	@Transactional
	public void insertQuest(Quest quest) {
		// 일단 퀘스트 생성
		questDao.insertQuest(quest);
		
		// task가 비어있으면 예외 던져 롤백
		if (quest.getTasks() == null | quest.getTasks().isEmpty()) {
			throw new IllegalArgumentException("No Tasks");
		}
		
		// 각 task 생성
		for (Task task : quest.getTasks()) {
			task.setQuestId(quest.getQuestId());
			taskService.createTask(task);
		}
		
	}

}
