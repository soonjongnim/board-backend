package com.soon.board.service;

import org.springframework.http.ResponseEntity;

import com.soon.board.dto.request.auth.SignInRequestDto;
import com.soon.board.dto.request.auth.SignUpRequestDto;
import com.soon.board.dto.response.auth.SignUpResponseDto;
import com.soon.board.dto.response.auth.SignInResponseDto;

public interface AuthService {

	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
