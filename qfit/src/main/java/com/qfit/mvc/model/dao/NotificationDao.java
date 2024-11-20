package com.qfit.mvc.model.dao;

import java.util.List;
import com.qfit.mvc.model.dto.Notification;

public interface NotificationDao {
	
	public void save(Notification notification);
	
	public List<Notification> getUnreadNotifications(int userId);
	
	public void updateRead(int notificationId);

	public Notification findById(int notificationId);

}
