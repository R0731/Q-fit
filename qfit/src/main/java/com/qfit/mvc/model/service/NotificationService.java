package com.qfit.mvc.model.service;

import java.util.List;

import com.qfit.mvc.model.dto.Notification;

public interface NotificationService {

	// 알림 생성
	public void sendNotification(int id, String message);
	
	// 읽지 않은 알림 리스트 불러오기
	public List<Notification> getUnreadNotifications(int id);
	
	// 읽음 처리
	public void asRead(int notificationId);
}
