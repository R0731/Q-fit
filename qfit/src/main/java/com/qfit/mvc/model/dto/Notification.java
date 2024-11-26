package com.qfit.mvc.model.dto;

public class Notification {

	private int notificationId; // 알림 고유 ID
	private int userId; // 유저 고유 ID
	private String message; // 알림 메시지 내용
	private String createdAt; // 알림 생성 시간
	private boolean isRead; // 읽음여부

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", userId=" + userId + ", message=" + message
				+ ", createdAt=" + createdAt + ", isRead=" + isRead + "]";
	}
	
}