package com.soon.board.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// JWT: 전자 서명이 된 토큰
// JSON 형태로 구성된 토큰
// {header}.{payload}.{signature}
// header: typ (해당 토큰의 타입), alg (토큰을  서명하기 위해 해시 알고리즘
// payload: sub (해당 토큰의 주인), iat ( 토큰이 발행된 시간), exp (토큰이 만료되는 시간)

@Component
public class TokenProvider {
	// JWT 생성 및 검증을 위한 키
	@Value("${jwt.secret-key}")
	private String SECURITY_KEY;
	
	// JWT 생성하는 메서드
	public String create (String email) {
		// 만료날짜를 현재 날짜 + 1시간으로 설정
		Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes(StandardCharsets.UTF_8));
		
		// JWT를 생성
		return Jwts.builder()
				// 암호화에 사용될 알고리즘, 키
				.signWith(key, SignatureAlgorithm.HS256)
				// JWT 제목, 생성일, 만료일
				.setSubject(email).setIssuedAt(new Date()).setExpiration(exprTime)
				// 생성
				.compact();
	}
	
	// JWT 검증
	public String validate (String token) {
		Claims claims = null;
		Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes(StandardCharsets.UTF_8));
		
		try {
			// 매개변수로 받은 token을 키를 사용해서 복호화 (디코딩)
			claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		// 복호화된 토큰의 payload에서 제목을 가져옴
		return claims.getSubject();
	}

}
