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

    /**
     * 읽지 않은 알림 조회 메서드
     * @param id 읽지 않은 알림을 조회할 사용자의 ID
     * @return 성공 시 HTTP 상태 200 (OK) 및 읽지 않은 알림 리스트 반환
     */
    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@RequestParam int id) {
        List<Notification> notifications = notificationService.getUnreadNotifications(id);
        return ResponseEntity.ok(notifications);
    }

    /**
     * 알림 읽음 처리 메서드
     * @param notificationId 읽음 처리할 알림의 ID
     * @return 성공 시 HTTP 상태 204 (NO_CONTENT) 반환
     */
    @PutMapping("/read")
    public ResponseEntity<Void> markAsRead(@RequestParam int notificationId) {
        notificationService.asRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 알림 생성 메서드
     * @param notification 생성할 알림 객체 (JSON 형식으로 요청 본문에 전달)
     * @return 성공 시 HTTP 상태 200 (OK) 반환
     */
    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(notification.getUserId(), notification.getMessage());
        return ResponseEntity.ok().build();
    }
}
