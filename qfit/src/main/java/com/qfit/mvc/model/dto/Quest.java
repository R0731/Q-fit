package com.qfit.mvc.model.dto;

import java.util.ArrayList;
import java.util.List;

public class Quest {
	private int questId; // 퀘스트 아이디
	private int traineeId; // 연관된 traineeId
	private int trainerId; // 연관된 trainerId
	private String startAt; // 퀘스트 시작 날짜
	private String endAt; // 퀘스트 종료 날짜
	private String createdAt; // 퀘스트 생성일
	private String updatedAt; // 퀘스트 수정일
	private List<Task> tasks = new ArrayList<>(); // task 목록
	private Review review; // 해당 퀘스트 리뷰
	private Feedback feedback; // 해당 퀘스트 피드백
	
	// 생성자
	public Quest() {
	}

	public Quest(int questId, int traineeId, int trainerId, String startAt, String endAt, List<Task> tasks) {
		this.questId = questId;
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.startAt = startAt;
		this.endAt = endAt;
		this.tasks = tasks;
	}

	// Getter 및 Setter 메서드
	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public String getStartAt() {
		return startAt;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}

	public String getEndAt() {
		return endAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	// toString
	@Override
	public String toString() {
		return "Quest [questId=" + questId + ", traineeId=" + traineeId + ", trainerId=" + trainerId + ", startAt="
				+ startAt + ", endAt=" + endAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", tasks="
				+ tasks + ", review=" + review + ", feedback=" + feedback + "]";
	}
	
}
