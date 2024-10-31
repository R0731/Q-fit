package com.qfit.mvc.model.dao;

import java.util.List;

import com.qfit.mvc.model.dto.TaskDto;


public interface TaskDao {
	
	// 전체 태스크 조회
	public List<TaskDto> selectAll();
	// 유산소 태스크 추가
	public void insertCardioTask(TaskDto taskDto);
	// 무산소 태스크 추가
	public void insertWeightTask(TaskDto taskDto);
	// 태스크 삭제
	public int deleteTask(int taskId);
	// 유산소 태스크 수정
	public void updateCardioTask(TaskDto taskDto);
	// 무산소 태스크 수정
	public void updateWeightTask(TaskDto taskDto);
	// 순서 수정
	public void updateOrder(TaskDto taskDto);
}
