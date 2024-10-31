package com.qfit.mvc.model.dto;

public class TaskDto {
	private int taskId;
	private int questId;
	private int order;
	private int kg;
	private int minute;
	private int count;
	private int exerciseId;
	private boolean isCompleted;
	
	public TaskDto(){}
	
	
//무산소 운동
	public TaskDto(int kg, int count, int exerciseId) {
		super();
		this.kg = kg;
		this.count = count;
		this.exerciseId = exerciseId;
	}


//유산소 운동
	public TaskDto(int minute, int exerciseId) {
		super();
		this.minute = minute;
		this.exerciseId = exerciseId;
	}



	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getKg() {
		return kg;
	}

	public void setKg(int kg) {
		this.kg = kg;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public String toString() {
		return "TaskDto [taskId=" + taskId + ", questId=" + questId + ", order=" + order + ", kg=" + kg + ", minute="
				+ minute + ", count=" + count + ", exerciseId=" + exerciseId + ", isCompleted=" + isCompleted + "]";
	}
	
	

}
