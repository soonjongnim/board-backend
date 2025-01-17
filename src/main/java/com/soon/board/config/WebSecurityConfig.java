package com.soon.board.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.soon.board.filter.JwtAuthencationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	JwtAuthencationFilter jwtAuthencationFilter;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
//			.cors(cors -> cors
//				.configurationSource(CorsConfigurationSource())
//			)
//			.csrf(CsrfConfigurer::disable)
//			.httpBasic(HttpBasicConfigurer::disable)
//			.sessionManagement(SessionManagement -> SessionManagement
//					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			)
//			.authorizeHttpRequests(request -> request
//					.requestMatchers(HttpMethod.GET, "/api/board/**", "/api/user/**", "/", "/api/auth/**", "/api/search/**", "/api/file/**", "/oauth/callback", "/api/payment/**", "/api/item/**").permitAll()
//					.anyRequest().permitAll()
////					.anyRequest().authenticated()
//			)
//			.exceptionHandling(exceptionHandling -> exceptionHandling
//					.authenticationEntryPoint(new FailedAuthenticationEntryPoint())
//			);
//			httpSecurity.addFilterBefore(jwtAuthencationFilter, UsernamePasswordAuthenticationFilter.class);
		
		// cors 정책 (현재는 Application에서 작업을 해뒀으므로 기본 설정 사용)
		.cors().and()
		// csrf 대책 (현재는 CSRF에 대한 대책을 비활성화)
		.csrf().disable()
		// Basic 인증 (현재는 Bearer token 인증방법을 사용하기 때문에 비활성화)
		.httpBasic().disable()
		// 세션을 기반 인증 (현재는 Session 기반 인증을 사용하지 않기 때문에 상태를 없앰)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		// "/", "/api/auth/**" 모듈에 대해서는 모두 허용 (인증을 하지 않고 사용 가능하게 함)
		.authorizeRequests()
		.antMatchers("/", "/api/auth/**", "/api/search/**", "/api/file/**", "/oauth/callback", "/api/payment/**", "/api/item/**")
		// 인증된 사용자만 
		.permitAll().antMatchers(HttpMethod.GET, "/api/board/**", "/api/user/**").permitAll()
		// 나머지 Request에 대해서는 모두 인증된 사용자만 사용가능하게 함
		.anyRequest().authenticated().and().exceptionHandling()
		.authenticationEntryPoint(new FailedAuthenticationEntryPoint());

		httpSecurity.addFilterBefore(jwtAuthencationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}
	
	
	//스프링3.0에서 설정할경우 사용
	@Bean
	protected CorsConfigurationSource CorsConfigurationSource() {
		
		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.addAllowedOrigin("*");
//		configuration.addAllowedMethod("*");
//		configuration.addExposedHeader("*");
		//허용할 url 설정
        configuration.addAllowedOrigin("http://localhost:3000");
        //허용할 헤더 설정
        configuration.addAllowedHeader("*");
        //허용할 http method
        configuration.addAllowedMethod("*");
        // 클라이언트가 접근 할 수 있는 서버 응답 헤더
//        configuration.addExposedHeader(TokenProperties.AUTH_HEADER);
//        configuration.addExposedHeader(TokenProperties.REFRESH_HEADER);
        //사용자 자격 증명이 지원되는지 여부
        configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		System.out.println("error");
		response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed.\" }");
	}
}