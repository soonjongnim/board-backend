package com.soon.board.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

//    private final Key key;

    @Value("${jwt.secret-key}") 
    private String SECURITY_KEY;
//    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }

    // JWT 생성하는 메서드
    public String create(String email) {
    	// 만료날짜를 현재 날짜 + 1시간으로 설정
		Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		byte[] decodedBytes = Decoders.BASE64.decode(SECURITY_KEY);
		Key key = Keys.hmacShaKeyFor(decodedBytes);
    	
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(exprTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 검증
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    private Claims parseClaims(String accessToken) {
    	Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes(StandardCharsets.UTF_8));
    	
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    
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
