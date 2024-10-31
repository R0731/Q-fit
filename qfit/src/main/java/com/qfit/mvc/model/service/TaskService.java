package com.qfit.mvc.model.service;

import java.util.List;

import com.qfit.mvc.model.dto.TaskDto;

public interface TaskService {
	
	// 전체 태스크 조회
	public List<TaskDto> getTaskList();
	// 태스크 추가
	public void addCardioTask(TaskDto taskDto);
	// 태스크 추가
	public void addWeightTask(TaskDto taskDto);
	// 태스크 삭제
	public boolean deleteTask(int taskId);
	// 태스크 수정
	public void updateCardioTask(TaskDto taskDto);
	// 태스크 수정
	public void updateWeightTask(TaskDto taskDto);
	// 순서 수정
	public void updateOrder(TaskDto taskDto);

}
