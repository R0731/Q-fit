package com.qfit.mvc.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.qfit.mvc.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	private final String HEADER_AUTH = "access-token";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * HTTP 요청을 사전 처리하는 메서드
	 * @param request 클라이언트의 HTTP 요청 객체
	 * @param response 서버의 HTTP 응답 객체
	 * @param handler 요청을 처리할 핸들러 객체
	 * @return 요청 처리를 계속 진행할 경우 true 반환, 중단 시 예외 발생
	 * @throws Exception 유효하지 않은 요청 또는 인증 실패 시 예외 발생
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 클라이언트가 서버와의 상호작용이 가능한지 확인하기 위해 OPTIONS 메서드로 사전 요청을 보낸 경우 처리 허용
		if(request.getMethod().equals("OPTIONS"))
			return true;
		
		String token = request.getHeader(HEADER_AUTH); // Authorization 헤더에서 토큰을 가져옴
		
		if(token != null) {
			jwtUtil.validate(token); // JWT 유효성 검사
			return true;
		}
		
		throw new Exception("유효하지 않은 접근입니다.");
	}
	
}
