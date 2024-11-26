package com.qfit.mvc.model.dto;

public class Task {
	private int taskId; // task 고유 ID
	private int questId; // quest 고유 ID
	private int orderIndex; // 순서 정렬에 사용할 요소
	private boolean isCompleted; // 달성 여부
	private Integer count; // 횟수
	private Integer weightKg; // 무게kg
	private Integer cardioMinutes; // 유산소 운동 시간
	private int exerciseId; // 운동 이름
	
	// 생성자
	public Task() {
	}
	
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
