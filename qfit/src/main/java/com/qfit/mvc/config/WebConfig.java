package com.qfit.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.qfit.mvc.interceptor.JwtInterceptor;
import com.qfit.mvc.interceptor.NotificationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private final JwtInterceptor jwtInterceptor;
    private final NotificationInterceptor notificationInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor, NotificationInterceptor notificationInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
        this.notificationInterceptor = notificationInterceptor;
    }
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
        		.allowedOrigins("*") // 모든 도메인 허용
		        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
		        .allowedHeaders("*"); // 모든 헤더 허용
						
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(notificationInterceptor)
        .addPathPatterns("/quest", "/review"); // 특정 경로로 제한
    }
	
}
