package com.qfit.mvc.model.service;

import java.util.List;

import com.qfit.mvc.model.dto.Task;

public interface TaskService {
	
	// 전체 태스크 조회
	public List<Task> getTaskList();
	
	// questId로 태스크 조회
	public List<Task> getTaskListdByQuestId(int questId);
	
	// isComplete true로 변경
	public void updateTaskCompleted(Task task);
	
	// 태스크 생성
	public void createTask(Task task);
	
	// 태스크 삭제
	public boolean deleteTask(int taskId);
	
	//태스크 수정
	public void updateTask(Task task);
	
	// 순서 수정
	public void updateOrder(Task task);

}