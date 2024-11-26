package com.qfit.mvc.model.dto;

public class Review {
	
	private int reviewId; // 리뷰 고유 ID
	private int questId; // 연관된 퀘스트의 ID
	private int traineeId; // 작성 트레이니 아이디
	private Difficulty difficulty; // 리뷰의 난이도
	private String createdAt; // 작성일
	
	// 난이도 열거형 선언
	public enum Difficulty {
		EASY, MEDIUM, HARD;
	}
	
	// 생성자
	public Review() {
	}
	
	public Review(int questId, int traineeId, Difficulty difficulty) {
		this.questId = questId;
		this.traineeId = traineeId;
		this.difficulty = difficulty;
	}
	
	// Getter 및 Setter 메서드
	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

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

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", questId=" + questId + ", difficulty=" + difficulty + "]";
	}
}