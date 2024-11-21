package com.qfit.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qfit.mvc.model.dto.Notification;
import com.qfit.mvc.model.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 읽지 않은 알림 조회
    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@RequestParam int userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // 알림 읽음 처리
    @PutMapping("/read")
    public ResponseEntity<Void> markAsRead(@RequestParam int notificationId) {
        notificationService.asRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    // 알림 생성
    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody Notification notification) {
    	System.out.println("@@@알림객체 조회" + " " + notification.getUserId() + notification.getMessage());
        notificationService.sendNotification(notification.getUserId(), notification.getMessage());
        return ResponseEntity.ok().build();
    }
}
