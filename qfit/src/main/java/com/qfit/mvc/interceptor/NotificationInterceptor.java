package com.qfit.mvc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfit.mvc.model.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public NotificationInterceptor(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        String requestURI = req.getRequestURI(); // 호출된 URI 경로
        String httpMethod = req.getMethod(); // HTTP 메서드

        if ("POST".equalsIgnoreCase(httpMethod)){
        	if("/quest".equals(requestURI) || "/review".equals(requestURI)) {
	            try {
	                // 요청 본문에서 JSON 데이터 파싱
	                JsonNode requestBody = objectMapper.readTree(req.getInputStream());
	                if (requestBody.has("numberId")) {
	                    int userId = requestBody.get("numberId").asInt(); // 사용자 ID 추출
	                    String message = requestBody.get("message").asText();
	                    // 알림 생성
	                    System.out.println("인터셉터" + userId + " " + message);
	                    notificationService.sendNotification(userId, message);
	                }
	            } catch (Exception e) {
	                // 요청 데이터가 없거나 잘못된 경우 로그 출력
	                System.err.println("Failed to parse request body: " + e.getMessage());
	            }
        	}

        }
        return true; // 요청 계속 진행
    }
}