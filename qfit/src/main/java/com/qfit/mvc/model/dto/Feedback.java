package com.qfit.mvc.model.dto;

public class Feedback {
	
	private int feedbackId; // 피드백 고유 ID
	private int questId; // 연관된 퀘스트 ID
	private int trainerId; // 작성 트레이너 ID
	private String content; // 피드백 내용
	private String createdAt; // 피드백 작성일
	
	// 생성자
	public Feedback() {
	}

	public Feedback(int questId, String content) {
		this.questId = questId;
		this.content = content;
	}

	// Getter 및 Setter 메서드
	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	// toString
	@Override
	public String toString() {
		return "Feedback [feedbackId=" + feedbackId + ", questId=" + questId + ", trainerId=" + trainerId + ", content="
				+ content + ", createdAt=" + createdAt + "]";
	}
	
}