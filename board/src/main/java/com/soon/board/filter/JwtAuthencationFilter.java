package com.soon.board.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.soon.board.provider.TokenProvider;

@Component
public class JwtAuthencationFilter extends OncePerRequestFilter {
	
	// request가 들어왔을 때 Request Header의 Authorization 필드의 Bearer Token을 가져옴
	// 가져온 토큰을 검증하고 검증 결과를 SecurityCotext에 추가
	
	@Autowired private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = parseBearerToken(request);
			if (token == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			if (token != null && !token.equalsIgnoreCase("null")) {
				// 토큰 검증해서 payload의 email 가져옴
				String email = tokenProvider.validate(token);
				
				if (email == null) {
					filterChain.doFilter(request, response);
					return;
				}
				
				// SecurityContext에 추가할 객체
				AbstractAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// SecurityContext에 AbstractAuthenticationToken 객체를 추가해서
				// 해당 Thread가 지속적으로 인증 정보를 가질수 있도록 해줌
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(securityContext);
			
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		filterChain.doFilter(request, response);
	}
	
	// Request Header의 Authorization 필드의 Bearer Token을 가져오는 메서드
	private String parseBearerToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		
		boolean hasAuthorization = StringUtils.hasText(authorization);
		if (!hasAuthorization) return null;
		
		boolean isBearer = authorization.startsWith("Bearer ");
		if (!isBearer) return null;
		
		String token = authorization.substring(7); // 7번째부터 가져옴
		return token;
	}

}
