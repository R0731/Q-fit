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
	private String key = "SSAFY_Qfit_NonMajor_JavaTrack_SecretKey";
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	
	// 사용자 정보를 이용해 토큰 생성
	public String createToken(int id, String userId, String name, int userType) {
		// 유효기간
		Date exp = new Date(System.currentTimeMillis() + 1000*60*60); // 1시간
		
		// JWT : 헤더 / 페이로드(데이터) / 서명
		return Jwts.builder()
				.header().add("typ", "JWT").and()
				.claim("id", id)
				.claim("userId", userId)
                .claim("name", name)
                .claim("userType", userType)
				.expiration(exp) // 만료 시간
				.signWith(secretKey)
				.compact(); // 문자열 형태로 반환
	}
	
	// 유효성 검증(실제로 내용물을 확인하기 위함은 아님)
	// 이거 실행시 에러나면 유효기간 지난거... 
	public Jws<Claims> validate(String token){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
	
	// 토큰에서 사용자 ID 추출
    public int extractId(String token) {
    	Jws<Claims> claims = validate(token);
        return ((Claims) claims).get("id", Integer.class);
    }
}
