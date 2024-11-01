package com.qfit.mvc.model.dao;

import java.util.List;

import com.qfit.mvc.model.dto.Task;


public interface TaskDao {
	
	// 전체 태스크 조회
	public List<Task> selectAll();
	// 유산소 태스크 추가
	public void insertCardioTask(Task task);
	// 무산소 태스크 추가
	public void insertWeightTask(Task task);
	// 태스크 삭제
	public int deleteTask(int taskId);
	// 유산소 태스크 수정
	public void updateCardioTask(Task task);
	// 무산소 태스크 수정
	public void updateWeightTask(Task task);
	// 순서 수정
	public void updateOrder(Task task);
}
