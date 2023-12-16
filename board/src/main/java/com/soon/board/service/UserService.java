package com.soon.board.service;

import org.springframework.http.ResponseEntity;

import com.soon.board.dto.response.user.GetSignInUserResponseDto;

public interface UserService {
	
	ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
