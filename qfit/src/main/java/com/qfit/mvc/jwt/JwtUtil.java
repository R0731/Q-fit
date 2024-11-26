package com.qfit.mvc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String key = "SSAFY_Qfit_NonMajor_JavaTrack_SecretKey"; // 비밀 키
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)); // SecretKey 객체 생성
	
	 /**
     * JWT 토큰 생성 메서드
     * @param id 사용자 고유 ID
     * @param userId 사용자 계정 ID
     * @param name 사용자 이름
     * @param userType 사용자 유형 (예: trainer = 1, trainee = 2 등)
     * @return 생성된 JWT 토큰 문자열
     */
	public String createToken(int id, String userId, String name, int userType) {
		Date exp = new Date(System.currentTimeMillis() + 1000*60*60); // 토큰 만료 시간 : 1000밀리초 * 60초 * 60분
		
		// JWT 생성: 헤더 / 페이로드(데이터) / 서명
		return Jwts.builder()
				.header() // 헤더 설정
				.add("typ", "JWT") // 토큰 유형
				.and()
				.claim("id", id) // 사용자 ID
				.claim("userId", userId) // 사용자 계정 ID
                .claim("name", name) // 사용자 이름
                .claim("userType", userType) // 사용자 유형
				.expiration(exp) // 만료 시간 설정
				.signWith(secretKey) // 서명
				.compact(); // 문자열 형태로 최종 토큰 반환
	}
	
	/**
     * JWT 토큰의 유효성을 검증하는 메서드
     * @param token 검증할 JWT 토큰
     * @return 토큰의 서명이 유효한 경우 Jws<Claims> 객체 반환
     */
	public Jws<Claims> validate(String token){
		return Jwts.parser() // 파서 객체 생성
				.verifyWith(secretKey) // 비밀키로 서명 검증
				.build()
				.parseSignedClaims(token); // 서명된 토큰의 클레임 반환
	}
	
	/**
     * JWT 토큰에서 사용자 ID를 추출하는 메서드
     * @param token 사용자 정보를 포함한 JWT 토큰
     * @return 토큰의 클레임에서 추출한 사용자 고유 ID
     */
    public int extractId(String token) {
    	Jws<Claims> claims = validate(token); // 유효성 검증 후 클레임 반환
        return ((Claims) claims).get("id", Integer.class); // 사용자 ID 추출
    }
}
