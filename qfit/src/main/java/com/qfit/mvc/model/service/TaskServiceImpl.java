package com.qfit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfit.mvc.model.dao.ExerciseDao;
import com.qfit.mvc.model.dao.TaskDao;
import com.qfit.mvc.model.dto.Exercise;
import com.qfit.mvc.model.dto.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	private final TaskDao taskDao;
	private final ExerciseDao exerciseDao;
	
	

	public TaskServiceImpl(TaskDao taskDao, ExerciseDao exerciseDao) {
		this.taskDao = taskDao;
		this.exerciseDao = exerciseDao;
	}

	//전체 태스크 조회
	@Override
	public List<Task> getTaskList() {
		System.out.println("모든 태스크를 가져왔습니다.");
		return taskDao.selectAll();
	}
	
	//퀘스트 ID로 태스크 조회
	@Override
	public List<Task> getTaskListdByQuestId(int questId) {
		System.out.println(questId+"번 퀘스트의 태스크를 가져왔습니다.");
		return taskDao.selectQuestId(questId);
	}
	
	//태스크 생성
	@Override
	public void createTask(Task task) {
		
		System.out.println("Requesting exercise with ID: " + task.getExerciseId());
		
		Exercise exercise = exerciseDao.selectExerciseByExId(task.getExerciseId());
        String exerciseType = exercise.getExerciseType();
        
        if (exercise == null) {
            throw new IllegalArgumentException("존재하지 않는 exerciseId입니다: " + task.getExerciseId());
        }
        System.out.println("@task조회" + task.toString());
        if ("Cardio".equals(exerciseType)) {
            if (task.getCardioMinutes() == null || task.getWeightKg() != null || task.getCount() != null) {
                throw new IllegalArgumentException("유산소 운동에는 cardioMinutes만 있어야 합니다.");
            }
            taskDao.insertCardioTask(task);
        } else if ("Weight".equals(exerciseType)) {
            if (task.getWeightKg() == null || task.getCount() == null || task.getCardioMinutes() != null) {
                throw new IllegalArgumentException("무산소 운동에는 weightKg과 count만 있어야 합니다.");
            }
            taskDao.insertWeightTask(task);
        } else {
            throw new IllegalArgumentException("유효하지 않은 운동 타입입니다.");
        } 
        
	}
	
	//태스크 삭제
	@Override
	public boolean deleteTask(int taskId) {
		System.out.println(taskId+"번 태스크를 삭제했습니다.");
		int result = taskDao.deleteTask(taskId);
		System.out.println(result);
		return result ==1;
	}
	
	//태스크 수정
	@Override
	public void updateTask(Task task) {
		 Exercise exercise = exerciseDao.selectExerciseByExId(task.getExerciseId()); // exerciseId로 exercise 조회
		 System.out.println("테스크운동조회" + exercise);
	        String exerciseType = exercise.getExerciseType(); // exerciseType 가져오기

	        if ("Cardio".equals(exerciseType)) {
	            if (task.getCardioMinutes() == null || task.getWeightKg() != null || task.getCount() != null) {
	                throw new IllegalArgumentException("유산소 운동에는 cardioMinutes만 있어야 합니다.");
	            }
	            taskDao.updateCardioTask(task);
	            System.out.println("태스크를 수정했습니다.");
	        } else if ("Weight".equals(exerciseType)) {
	            if (task.getWeightKg() == null || task.getCount() == null || task.getCardioMinutes() != null) {
	                throw new IllegalArgumentException("무산소 운동에는 weightKg과 count만 있어야 합니다.");
	            }
	            taskDao.updateWeightTask(task);
	            System.out.println("태스크를 수정했습니다.");
	        } else {
	            throw new IllegalArgumentException("유효하지 않은 운동 타입입니다.");
	        }
	    }

	// 순서 수정
	@Override
	public void updateOrder(Task task) {
		System.out.println("태스크 순서를 수정했습니다.");
		taskDao.updateOrder(task);
	}
	
	// isComplete 수정
	@Override
	public void updateTaskCompleted(Task task) {
		taskDao.updateTaskCompleted(task);
	}

}
