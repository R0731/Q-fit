package com.qfit.mvc.model.dto;

public class TaskDto {
	private int taskId;
	private int questId;
	private int orderIndex;
	private boolean isCompleted;
	private int count;
	private int weightKg;
	private int cardioMinutes;
	private int exerciseId;
	
	public TaskDto() {
		// TODO Auto-generated constructor stub
	}
	
	// 무산소 생성자
	public TaskDto(int questId, boolean isCompleted, int count, int weightKg, int cardioMinutes, int exerciseId) {
		this.questId = questId;
		this.count = count;
		this.weightKg = weightKg;
		this.exerciseId = exerciseId;
	}
	
	// 유산소 생성자
	public TaskDto(int questId, int cardioMinutes, int exerciseId) {
		super();
		this.questId = questId;
		this.cardioMinutes = cardioMinutes;
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
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getWeightKg() {
		return weightKg;
	}
	public void setWeightKg(int weightKg) {
		this.weightKg = weightKg;
	}
	public int getCardioMinutes() {
		return cardioMinutes;
	}
	public void setCardioMinutes(int cardioMinutes) {
		this.cardioMinutes = cardioMinutes;
	}
	public int getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	@Override
	public String toString() {
		return "TaskDto [taskId=" + taskId + ", questId=" + questId + ", orderIndex=" + orderIndex + ", isCompleted="
				+ isCompleted + ", count=" + count + ", weightKg=" + weightKg + ", cardioMinutes=" + cardioMinutes
				+ ", exerciseId=" + exerciseId + "]";
	}
	
	

}
