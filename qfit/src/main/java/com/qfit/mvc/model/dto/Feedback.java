package com.qfit.mvc.model.dto;

public class Feedback {
	
	private int feedbackId;
	private int questId;
	private int trainerId;
	private String content;
	private String createdAt;
	
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