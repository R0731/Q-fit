package com.qfit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfit.mvc.model.dao.TaskDao;
import com.qfit.mvc.model.dto.TaskDto;

@Service
public class TaskServiceImpl implements TaskService {
	
	private final TaskDao taskDao;
	
	

	public TaskServiceImpl(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<TaskDto> getTaskList() {
		System.out.println("모든 태스크를 가져왔습니다.");
		return taskDao.selectAll();
	}

	@Override
	public void addCardioTask(TaskDto taskDto) {
		System.out.println("유산소 태스크를 생성했습니다.");
		taskDao.insertCardioTask(taskDto);}
		
	@Override
	public void addWeightTask(TaskDto taskDto) {
		System.out.println("무산소 태스크를 생성했습니다.");
		taskDao.insertWeightTask(taskDto);
			
	}

	@Override
	public boolean deleteTask(int taskId) {
		System.out.println(taskId+"번 태스크를 삭제했습니다.");
		int result = taskDao.deleteTask(taskId);
		System.out.println(result);
		return result ==1;
	}

	@Override
	public void updateCardioTask(TaskDto taskDto) {
		System.out.println("유산소 태스크를 수정했습니다.");
		taskDao.updateCardioTask(taskDto);
	}
	
	@Override
	public void updateWeightTask(TaskDto taskDto) {
		System.out.println("무산소 태스크를 수정했습니다.");
		taskDao.updateWeightTask(taskDto);
	}

	@Override
	public void updateOrder(TaskDto taskDto) {
		System.out.println("태스크 순서를 수정했습니다.");
		taskDao.updateOrder(taskDto);
	}

}
