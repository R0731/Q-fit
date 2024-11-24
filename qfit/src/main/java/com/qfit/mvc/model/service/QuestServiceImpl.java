package com.qfit.mvc.model.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
		return questDao.getQuestByTraineeId(traineeId, startAt);
	}

	// 퀘스트 생성
	@Override
	@Transactional
	public void insertQuest(Quest quest) {
		// Qeust가 이미 존재하면 처리
		if (questDao.questExists(quest.getTraineeId(), quest.getStartAt()) >= 1)
			throw new IllegalStateException("A feedback for this quest has already been submitted.");
		
		// startAt이 null이 아닌 경우 endAt 계산
        if (quest.getStartAt() != null && !quest.getStartAt().isEmpty()) {
            String endAt = calculateEndAt(quest.getStartAt());
            quest.setEndAt(endAt);
        }
        
		// 일단 퀘스트 생성
		questDao.insertQuest(quest);
		
		System.out.println("퀘스트아이디확인 " + quest.getQuestId());
		
		// 각 task 생성
		for (Task task : quest.getTasks()) {
			task.setQuestId(quest.getQuestId());
			System.out.println("테스크아이디확인" + " " + task.getQuestId());
			taskService.createTask(task);
		}
		
		// task가 비어있으면 예외 던져 롤백
		if (quest.getTasks() == null | quest.getTasks().isEmpty()) {
			throw new IllegalArgumentException("No Tasks");
		}
	}

	// 24시간 뒤의 endAt 계산
    private String calculateEndAt(String startAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startAt, formatter);
        LocalDateTime endDateTime = startDateTime.plusHours(24); // 24시간 더하기
        return endDateTime.format(formatter); // "yyyy-MM-dd HH:mm:ss" 형식으로 반환
    }

    // 퀘스트 수행률 계산
    @Override
    public List<Map<String, Object>> getTraineeQuestCompletionRate(int traineeId, String startAt, String endAt) {
    	System.out.println("2");
        return questDao.getTraineeQuestCompletionRate(traineeId, startAt, endAt);
    }
	
    // 퀘스트 상태 출력
    @Override
    public List<Map<String, Object>> getQuestStatuses(int trainerId, String startAt){
    	return questDao.getTraineeQuestStatus(trainerId, startAt);
    };
	
}
