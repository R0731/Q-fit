package com.qfit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfit.mvc.model.dao.NotificationDao;
import com.qfit.mvc.model.dto.Notification;

@Service
public class NotificationServiceImpl implements NotificationService{

	private final NotificationDao notificationDao;
	
	public NotificationServiceImpl(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}
	
	// 알림 생성
	@Override
	public void sendNotification(int id, String message) {
		if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be null or empty.");
        }
		Notification notification = new Notification(); // 알림 객체 생성
        notification.setUserId(id);
        notification.setMessage(message);
        notificationDao.save(notification); // 알림 저장
	}

	// 아직 읽지 않은 알림 가져오기
	@Override
	public List<Notification> getUnreadNotifications(int id) {
		return notificationDao.getUnreadNotifications(id);
	}

	// 알림 읽음처리
	@Override
	public void asRead(int notificationId) {
        notificationDao.updateRead(notificationId);
	}

}
