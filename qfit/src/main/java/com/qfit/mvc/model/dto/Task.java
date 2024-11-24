package com.qfit.mvc.model.dto;

public class Task {
	private int taskId;
	private int questId;
	private int orderIndex;
	private boolean isCompleted;
	private Integer count;
	private Integer weightKg;
	private Integer cardioMinutes;
	private int exerciseId;
	
	/*
	 * 기본 Task 생성자
	 */
	public Task() {
	}
	
//	// 전체 생성자
//	public Task(int questId, boolean isCompleted, Integer count, Integer weightKg, Integer cardioMinutes, int exerciseId) {
//		this.questId = questId;
//		this.count = count;
//		this.weightKg = weightKg;
//		this.exerciseId = exerciseId;
//	}
	
	// 무산소 생성자
	public Task(int questId, Integer count, Integer weightKg, int exerciseId) {
		this.questId = questId;
		this.count = count;
		this.weightKg = weightKg;
		this.exerciseId = exerciseId;
	}
	
	// 유산소 생성자
	public Task(int questId, Integer cardioMinutes, int exerciseId) {
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getWeightKg() {
		return weightKg;
	}
	public void setWeightKg(Integer weightKg) {
		this.weightKg = weightKg;
	}
	public Integer getCardioMinutes() {
		return cardioMinutes;
	}
	public void setCardioMinutes(Integer cardioMinutes) {
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
		return "Task [taskId=" + taskId + ", questId=" + questId + ", orderIndex=" + orderIndex + ", isCompleted="
				+ isCompleted + ", count=" + (count != null ? count : 0) + ", weightKg=" + (weightKg != null ? weightKg : 0) + ", cardioMinutes=" + (cardioMinutes != null ? cardioMinutes : 0)
				+ ", exerciseId=" + exerciseId + "]";
	}
	
	

}
