package com.qfit.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.qfit.mvc.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
        		.allowedOrigins("*") // 모든 도메인 허용
		        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
		        .allowedHeaders("*"); // 모든 헤더 허용
						
	}
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor((jwtInterceptor).addPathPatterns("/**");
//	}
}
