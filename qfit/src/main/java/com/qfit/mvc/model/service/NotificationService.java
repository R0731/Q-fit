package com.qfit.mvc.model.service;

import java.util.List;

import com.qfit.mvc.model.dto.Notification;

public interface NotificationService {

	public void sendNotification(int id, String message);
	
	public List<Notification> getUnreadNotifications(int id);
	
	public void asRead(int notificationId);
}
