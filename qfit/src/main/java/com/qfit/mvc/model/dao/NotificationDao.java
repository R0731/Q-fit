package com.qfit.mvc.model.dao;

import java.util.List;
import com.qfit.mvc.model.dto.Notification;

public interface NotificationDao {
	
	// 생성된 알림 저장
	public void save(Notification notification);
	
	// 유저의 id에 따라 아직 읽음 처리 되지 않은 알림 리스트 반환 
	public List<Notification> getUnreadNotifications(int id);
	
	// 알림 id에 따라 읽음 처리
	public void updateRead(int notificationId);

	// 해당 알림 id에 맞는 알림 객체 반환
	public Notification findById(int notificationId);

}
